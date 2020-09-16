package engine.Controller;

import com.sun.istack.Nullable;
import engine.DBModel.Completions;
import engine.DBModel.DBQuiz;
import engine.DBModel.DBUser;
import engine.DBModel.QuizAnswer;
import engine.DBQuizRepository;
import engine.ExceptionHandler.UserAlreadyExistException;
import engine.ExceptionHandler.UserNotAuthorizedToDeleteQuiz;
import engine.Model.*;

import engine.ObjectRepository.QuizRepository;
import engine.SpringSecurity.MyUserDetails;
import engine.SpringSecurity.MyUserDetailsService;
import engine.UserRepository.CompletedQuizzesRepository;
import engine.UserRepository.UserPagenationRepository;
import engine.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.*;
import java.util.stream.Collectors;

@RestController()
public class QuizController {
    @Autowired
    UserRepository userRepository;
    //private List<Quiz> quizzes = new ArrayList<>();
    private QuizRepository quizzes;
    @Autowired
    private DBQuizRepository dbQuizRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //6 step of Web Quiz Engine app
    @Autowired
    UserPagenationRepository userPagenationRepository;

    @Autowired
    CompletedQuizzesRepository completedQuizzesRepository;


    @Autowired
    public QuizController(QuizRepository repository) {
        this.quizzes = repository;

    }
    @GetMapping("api/user")
    public ResponseEntity<List<DBUser>> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(userRepository.findAll(),headers,HttpStatus.OK);
    }

    @PostMapping("api/register")
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel user) {


            Optional<DBUser> checkUser = userRepository.findUserByEmail(user.getEmail());
            if (checkUser.isPresent()) {
                throw new UserAlreadyExistException("User already exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(new DBUser(user.getEmail(), user.getPassword(), "user"));
            return new ResponseEntity<>(user, null,HttpStatus.OK);
    }

    @GetMapping("api/quiz")
    public ResponseEntity<Quiz> getQuiz() {
        ArrayList<String> options = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        options.add("Robot");
        options.add("Tea leaf");
        options.add("Cup of coffee");
        options.add("Bug");
        Quiz quiz = new Quiz("The Java Logo","What is depicted on the Java logo?", options, null);
        return new ResponseEntity(quiz, headers, HttpStatus.OK);
    }

    @PostMapping(value = "api/quiz",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<QuizResponse> solveQuiz(@ModelAttribute("answer") int answer) {
        QuizResponse response;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (answer == 2) {
            response = new QuizResponse(true, "Congratulations, you're right!");
        }
        else {
            response = new QuizResponse(false, "Wrong answer! Please, try again.");
        }
        return new ResponseEntity(response,headers,HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> addQuiz(@Valid @RequestBody Quiz quiz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Object principal =  SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
        String auther = ((MyUserDetails) principal).getEmail();
        DBQuiz dbQuiz = new DBQuiz(quiz.getText(), quiz.getTitle(), quiz.convToListOfQuizOptions(), quiz.convToListOfAnswerOptions(), auther);
        dbQuizRepository.save(dbQuiz);
        quiz.setId((int) dbQuiz.getQuizId());
        return new ResponseEntity(quiz, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/api/quizzes/{id}")
    public ResponseEntity getQuizbyId(@PathVariable long id) {
        DBQuiz quiz;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            quiz = dbQuizRepository.findById(id).get();
        } catch (Exception e) {
            return new ResponseEntity(null,headers,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(quiz.toQuiz(),headers, HttpStatus.OK);
    }

    @GetMapping(value = "/api/quizzes/completed")
    public ResponseEntity<pagingDetails> getAllCompletedQuizzes(@RequestParam(required=false) Optional<Integer> page, @RequestParam(required=false) Optional<Integer> pageSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Pageable paging = PageRequest.of(page.orElse(0), pageSize.orElse(10), Sort.by("completedAt").descending());
        Object principal =  SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
        String auther = ((MyUserDetails) principal).getEmail();
        Page<Completions> pagedResult = completedQuizzesRepository.getAllByUserEmail(auther,paging);
        return new ResponseEntity(new pagingDetails(pagedResult), headers, HttpStatus.OK);

    }
    @GetMapping(value = "/api/quizzes")
    public ResponseEntity<pagingDetails> getAllQuizzes(@RequestParam(required=false) Optional<Integer> page,@RequestParam(required=false) Optional<Integer> pageSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Pageable paging = PageRequest.of(page.orElse(0), pageSize.orElse(10));
        Page<DBQuiz> pagedResult = userPagenationRepository.findAll(paging);
        pagingDetails pageD = new pagingDetails();
        return new ResponseEntity(pageD.toQuizPageingDetails(pagedResult), headers, HttpStatus.OK);

    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity solve(@Valid @RequestBody Answer answer, @PathVariable int id) {
        QuizResponse response;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DBQuiz quiz = null;
        String auther = null;
        try {
            quiz = dbQuizRepository.findById((long) id).get();
            Object principal =  SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
            auther = ((MyUserDetails) principal).getEmail();
        } catch (Exception e) {
            response = new QuizResponse(false, "404 (Not found)");
            return new ResponseEntity(response, headers, HttpStatus.NOT_FOUND);
        }
        if (Arrays.equals(answer.getAnswer(),convertToarr(quiz.getAnswers()))) {
            response = new QuizResponse(true,"Congratulations, you're right!");
            completedQuizzesRepository.save(new Completions(auther, id, new Date()));
        }
        else {
            response = new QuizResponse(false,"Wrong answer! Please try again.");
        }

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }
    public int[] convertToarr(List<QuizAnswer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getValue();
        }
        return result;
    }


    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@Valid @PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DBQuiz quiz;
        MyUserDetails userDetails;
        try {
            userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            quiz = dbQuizRepository.findDBquizByQuizId(id).get();
        } catch (Exception e) {
                return new ResponseEntity<>("Quiz not found",headers, HttpStatus.NOT_FOUND);
    }
            if (userDetails.getEmail().equals(quiz.getAuther())) {
                dbQuizRepository.deleteById(id);
            } else {
                throw new UserNotAuthorizedToDeleteQuiz("Not authorized to delete this quiz");
            }

        return new ResponseEntity("Deleted quiz id = "+id, headers, HttpStatus.NO_CONTENT);
    }

/*    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }*/
}

