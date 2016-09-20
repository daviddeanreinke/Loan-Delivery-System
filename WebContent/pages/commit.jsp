 <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main commit">
	<div class="title">
		<h2>Analyze and Commit Loans</h2>
	</div>
	
	<c:choose>
		<c:when test="${sessionScope.sessionLoans != undefined}">
		
			Verified Loans
			<table class="approvedLoans" style="border-bottom: 1px solid black">
				<tr><th>Name</th><th>Type</th><th>Amount</th><th>Compliant</th><th>Approved</th><th></th></tr>
				<c:forEach items="${sessionScope.sessionLoans}" var="loan">
				<tr>
					<td>${loan.borrowerName}</td>
					<td>${loan.type}</td>
					<td>${loan.amount}</td>
					<td>${loan.compliant}</td>
					<td>${loan.approved}</td>
					<td></td>
				</tr>
				</c:forEach>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><html:form action="commit.do" method ="POST">
					<input type="submit" name="method" value="commit">
					</html:form>
				</td>
			</table>
		
		</c:when>
		</c:choose>
	
	
	<html:form action="verify.do" method ="POST">
		<table>
			<tr>
				<th>Verify&nbspNew&nbspLoan</th>
				<th></th>
				<th>Institution ID: ${sessionScope.instID }</th>
				<th></th>
			</tr>
			<tr>
				<td>Type:</td>
				<td><html:select property="type">
						<html:option value="Auto">Auto</html:option>
						<html:option value="Business">Business</html:option>
						<html:option value="Home-Equity">Home-Equity</html:option>
						<html:option value="Mortgage">Mortgage</html:option>
						<html:option value="Personal">Personal</html:option>
						<html:option value="Student">Student</html:option>
					</html:select></td>
				<td>Borrower Name:</td>
				<td><html:text property="borrowerName"/></td>
			<tr>
			<tr>
				<td>Secured:</td>
				<td><html:select property="secured">
						<html:option value="yes">Yes</html:option>
						<html:option value="no">No</html:option>
					</html:select></td>
				<td>Borrower Tax ID:</td>
				<td><html:text property="taxID"/></td>
			<tr>
				<td>Amount:</td>
				<td><html:text property="amount"/></td>
				<td>Borrower Credit Score:</td>
				<td><html:text property="creditScore"/></td>
			</tr>
			<tr>
				<td>Interest Rate:</td>
				<td><html:text property="interestRate"/></td>
				<td>Monthly Payment:</td>
				<td><html:text property="monthly_payment"/></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>Duration:</td>
				<td><html:text property="duration"/></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>Delinquency:</td>
				<td><html:text property="delinquency"/></td>
			</tr>
			<tr>
				<td>Date of Issue:</td>
				<td><html:text property="issue_date" /></td>
				<td>YYYY-MM-DD</td>
				<td></td>
			</tr>
			<tr>
				<td>Comments:</td>
				<td colspan="3"><html:textarea rows="4" cols="50" property="comments">
								</html:textarea></td>
			</tr>
			<tr>
				<td><input type="submit" name="method" value="verify"></td>
			</tr>
		</table>
	</html:form>
		<html:errors/>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		document.forms.reset();
	});
</script>