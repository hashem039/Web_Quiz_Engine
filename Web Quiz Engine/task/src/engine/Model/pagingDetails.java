package engine.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.DBModel.Completions;
import engine.DBModel.DBQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class pagingDetails {

        private float totalPages;
        private float totalElements;
        private boolean last;
        private boolean first;
        Sort SortObject;
        private float number;
        private float numberOfElements;
        private float size;
        private boolean empty;
        Pageable PageableObject;
        @JsonProperty
        List< Object > content;

    public pagingDetails toQuizPageingDetails(Page<DBQuiz> page) {
        this.content = page.getContent().stream().map(e -> e.toQuiz()).collect(Collectors.toList());//.stream().map(e -> e.toQuiz()).collect(Collectors.toList());
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.SortObject = page.getSort();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.empty = page.isEmpty();
        this.PageableObject = page.getPageable();
        return this;
    }

    public pagingDetails() {
    }

    public pagingDetails(Page<Completions> page) {
        this.content = Arrays.asList(page.getContent().toArray());//.stream().map(e -> e.toQuiz()).collect(Collectors.toList());
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.SortObject = page.getSort();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.empty = page.isEmpty();
        this.PageableObject = page.getPageable();
    }

// Getter Methods

        public float getTotalPages() {
            return totalPages;
        }

        public float getTotalElements() {
            return totalElements;
        }

        public boolean getLast() {
            return last;
        }

        public boolean getFirst() {
            return first;
        }

        public Sort getSort() {
            return SortObject;
        }

        public float getNumber() {
            return number;
        }

        public float getNumberOfElements() {
            return numberOfElements;
        }

        public float getSize() {
            return size;
        }

        public boolean getEmpty() {
            return empty;
        }

        public Pageable getPageable() {
            return PageableObject;
        }

        // Setter Methods

        public void setTotalPages(float totalPages) {
            this.totalPages = totalPages;
        }

        public void setTotalElements(float totalElements) {
            this.totalElements = totalElements;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public void setSort(Sort sortObject) {
            this.SortObject = sortObject;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public void setNumberOfElements(float numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public void setSize(float size) {
            this.size = size;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public void setPageable(Pageable pageableObject) {
            this.PageableObject = pageableObject;
        }
    }

