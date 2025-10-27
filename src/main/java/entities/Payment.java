package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="Payment.findAll", query="select p from Payment p"), 
	@NamedQuery(name = "Payment.findByMemberId", query = "select p from Payment p where p.memberId=:id")
})

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int paymentId;
	
	@ManyToOne
	private int memberId;
	
	private double amountPaid;
	private String datePaid;
	
	public Payment() {
		
	}
	
	public Payment(int memberId, double amountPaid, String datePaid) {
		this.memberId = memberId;
		this.amountPaid = amountPaid;
		this.datePaid = datePaid;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}
	
}
