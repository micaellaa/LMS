/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 *
 * @author micaella
 */
@Entity
public class LendAndReturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendId;
    
    // private Long memberId;
    // private Long bookId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    
    @Temporal(TemporalType.DATE)
    private Date lendDate;
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal finalAmount;
    
    @Column
    private boolean isActive;
    
    @Column
    private boolean isPaid = false;
    
    
    
    public LendAndReturn() {
    }
    
    public LendAndReturn(Date lendDate, Date returnDate) {
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFinalAmount() {
        // for calculating fine amount
        System.out.println("BigDecimal getFineAmount(): " + this.lendId);
        long msInADay = 86400000;
        long msIn2Weeks = 1209600000;
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        System.out.println("BigDecimal getFineAmount(): finableMs = " + today.toString());
            
        long finableMs = Math.abs(today.getTime().getTime() - lendDate.getTime()) - msIn2Weeks;
        System.out.println("BigDecimal getFineAmount(): finableMs = " + finableMs);
        
        if (finableMs > 0) {
            long fineAmount = (long) (((float) finableMs / (float) msInADay) * 0.5);
            this.setFinalAmount(BigDecimal.valueOf(fineAmount));
            System.out.println("BigDecimal getFineAmount(): fineAmount = " + fineAmount);
            System.out.println("BigDecimal getFineAmount(): this.finalAmount = " + this.finalAmount);
            return BigDecimal.valueOf(fineAmount);
        }
        
        this.setFinalAmount(BigDecimal.ZERO);
        return BigDecimal.ZERO;
    }
    
    public String getFineAmount() {
        // for calculating fine amount
        System.out.println("String getFineAmount(): " + this.lendId);
        long msInADay = 86400000;
        long msIn2Weeks = 1209600000;
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
            
        long finableMs = Math.abs(today.getTime().getTime() - lendDate.getTime()) - msIn2Weeks;
        
        if (finableMs > 0) {
            long fineAmount = (long) finableMs / msInADay * (long) 0.5;
            this.setFinalAmount(new BigDecimal(fineAmount));
            return String.format("%.2f", fineAmount);
        }
        
        this.setFinalAmount(BigDecimal.ZERO);
        return "0";
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendId != null ? lendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendId fields are not set
        if (!(object instanceof LendAndReturn)) {
            return false;
        }
        LendAndReturn other = (LendAndReturn) object;
        if ((this.lendId == null && other.lendId != null) || (this.lendId != null && !this.lendId.equals(other.lendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LendAndReturn[ id=" + lendId + " ]";
    }
    
}
