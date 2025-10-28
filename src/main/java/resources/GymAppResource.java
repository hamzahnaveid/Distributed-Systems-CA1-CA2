package resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import daos.GymDAO;
import daos.MemberDAO;
import daos.PaymentDAO;
import daos.PlanDAO;
import entities.Gym;
import entities.Member;
import entities.Payment;
import entities.Plan;

@Path("/app")
public class GymAppResource {
	
	// Endpoints for CRUD methods on Member objects
	@GET
	@Path("/members")
	@Produces("application/json")
	public List<Member> listMembersJSON() {
		MemberDAO memberDao = new MemberDAO();
		return memberDao.getAllMembers();
	}
	
	@GET
	@Path("/members/{memberId}")
	@Produces("application/xml")
	public Member getMemberById(@PathParam("memberId") int memberId) {
		MemberDAO memberDao = new MemberDAO();
		return memberDao.findMemberById(memberId);
	}
	
	@POST
	@Path("/createMember")
	@Consumes("application/json")
	@Produces("text/plain")
	public String addMemberToDbJSON(Member member) {
		MemberDAO memberDao = new MemberDAO();
		memberDao.persist(member);
		return "Member added to DB : " + member.getName();
	}
	
	@PUT
	@Path("/updateMemberPayments/{memberId}")
	@Produces("application/json")
	public Member updateMemberPaymentsToDbJSON(
			@PathParam("memberId") int memberId, 
			@QueryParam("amountPaid") double amountPaid, 
			@QueryParam("datePaid") String datePaid
			) {
		MemberDAO memberDao = new MemberDAO();
		Member member = memberDao.findMemberById(memberId);
		
		Payment payment = new Payment(amountPaid, datePaid);
		PaymentDAO paymentDao = new PaymentDAO();
		paymentDao.persist(payment);
		
		member.addPayment(payment);
		Member updatedMember = memberDao.merge(member);
		return updatedMember;
	}
	
	@DELETE
	@Path("/deleteMember/{memberId}")
	@Produces("text/plain")
	public String deleteMemberFromDb(@PathParam("memberId") int memberId) {
		MemberDAO memberDao = new MemberDAO();
		Member member = memberDao.findMemberById(memberId);
		memberDao.remove(member);
		return "Member removed from DB : " + member.getName();
	}
	
	// Endpoints for CRUD methods on Payment objects
	@GET
	@Path("/payments")
	@Produces("application/json")
	public List<Payment> listPaymentsJSON() {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPayments();
	}
	
	@POST
	@Path("/createPayment")
	@Produces("text/plain")
	public String addPaymentToDb(@QueryParam("amountPaid") double amountPaid, @QueryParam("datePaid") String datePaid) {
		Payment payment = new Payment(amountPaid, datePaid);
		PaymentDAO paymentDao = new PaymentDAO();
		paymentDao.persist(payment);
		return "Payment added to DB: " + payment.getAmountPaid();
	}
	
	@PUT
	@Path("/updatePaymentAmount/{paymentId}")
	@Produces("application/xml")
	public Payment updatePaymentAmountToDbXML(@PathParam("paymentId") int paymentId, @QueryParam("amountPaid") double amountPaid) {
		PaymentDAO paymentDao = new PaymentDAO();
		Payment payment = paymentDao.findPaymentById(paymentId);
		payment.setAmountPaid(amountPaid);
		return paymentDao.merge(payment);
	}
	
	@DELETE
	@Path("/deletePayment/{paymentId}")
	@Produces("text/plain")
	public String deletePaymentFromDb(@PathParam("paymentId") int paymentId) {
		PaymentDAO paymentDao = new PaymentDAO();
		Payment payment = paymentDao.findPaymentById(paymentId);
		paymentDao.remove(payment);
		return "Payment removed from DB : " + payment.getAmountPaid() + " | " + payment.getDatePaid();
	}
	
	// Endpoints for CRUD methods on Plan objects
	@GET
	@Path("/plans")
	@Produces("application/json")
	public List<Plan> listPlansJSON() {
		PlanDAO planDao = new PlanDAO();
		return planDao.getAllPlans();
	}
	
	@POST
	@Path("/createPlan")
	@Produces("text/plain")
	public String addPlanToDb(@QueryParam("description") String description, @QueryParam("totalCost") double totalCost) {
		Plan plan = new Plan(description, totalCost);
		PlanDAO planDao = new PlanDAO();
		planDao.persist(plan);
		return "Plan added to DB : " + plan.getDescription();
	}
	
	@PUT
	@Path("/updatePlanDescription/{planId}")
	@Produces("application/json")
	public Plan updatePlanDescriptionToDbJSON(@PathParam("planId") int planId, @QueryParam("description") String description) {
		PlanDAO planDao = new PlanDAO();
		Plan plan = planDao.findPlanById(planId);
		plan.setDescription(description);
		return planDao.merge(plan);
	}
	
	@DELETE
	@Path("/deletePlan/{planId}")
	@Produces("text/plain")
	public String deletePlanFromDb(@PathParam("planId") int planId) {
		PlanDAO planDao = new PlanDAO();
		Plan plan = planDao.findPlanById(planId);
		planDao.remove(plan);
		return "Member removed from DB : " + plan.getDescription();
	}
	
	// Endpoints for CRUD methods on Gym objects
	@GET
	@Path("/gyms")
	@Produces("application/json")
	public List<Gym> listGymJSON() {
		GymDAO gymDao = new GymDAO();
		return gymDao.getAllGyms();
	}
	
	@POST
	@Path("/createGym")
	@Produces("text/plain")
	public String addGymToDb() {
		Gym gym = new Gym();
		GymDAO gymDao = new GymDAO();
		gymDao.persist(gym);
		return "Gym added to DB : ID = " + gym.getGymId();
	}
	
	@PUT
	@Path("/updateGymMembers/{gymId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Gym updateGymMembersToDbJSON(@PathParam("gymId") int gymId, Member member) {
		GymDAO gymDao = new GymDAO();
		Gym gym = gymDao.findGymById(gymId);
		gym.addMember(member);
		return gymDao.merge(gym);
	}
	
	@DELETE
	@Path("/deleteGym/{gymId}")
	@Produces("text/plain")
	public String deleteGymFromDb(@PathParam("gymId") int gymId) {
		GymDAO gymDao = new GymDAO();
		Gym gym = gymDao.findGymById(gymId);
		gymDao.remove(gym);
		return "Member removed from DB : " + gym.getGymId();
	}

}
