package com.library.catalog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String rackNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopyStatus status;

    // ✅ Default Constructor (Required by JPA)
    public BookCopy() {
    }

    // ✅ Parameterized Constructor
    public BookCopy(Long id, Long bookId, String rackNo, CopyStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.rackNo = rackNo;
        this.status = status;
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getRackNo() {
        return rackNo;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public void setStatus(CopyStatus status) {
        this.status = status;
    }
}
