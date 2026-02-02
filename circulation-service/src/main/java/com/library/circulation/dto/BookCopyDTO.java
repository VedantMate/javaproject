package com.library.circulation.dto;

public class BookCopyDTO {
    private Long id;
    private Long bookId;
    private String copyNumber;
    private String status;

    public BookCopyDTO() {
    }

    public BookCopyDTO(Long id, Long bookId, String copyNumber, String status) {
        this.id = id;
        this.bookId = bookId;
        this.copyNumber = copyNumber;
        this.status = status;
    }

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

    public String getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(String copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
