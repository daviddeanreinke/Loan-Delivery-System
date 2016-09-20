<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<div class="main terms">
	<div class="title">
		<h2>Browse and Purchase Loans</h2>
	</div>

	<table class="table table-striped approvedLoans" style="border-bottom: 1px solid black">
		<thead>
		<tr>
			<th>Loan ID</th>
			<th>Type</th>
			<th>Amount</th>
			<th>Monthly<br>Payment</th>
			<th>Interest<br>Rate</th>
			<th>Credit<br>Score</th>
			<th>Risk</th>
			<th></th>
			<th></th>
		</tr>
		</thead>
		<c:forEach items="${sessionScope.approvedLoans}" var="loan">
			<tr>
				<td>${loan.loanID}</td>
				<td>${loan.type}</td>
				<td>$${loan.amount}</td>
				<td>${loan.monthly_payment}</td>
				<td>${loan.interestRate}%</td>
				<td>${loan.creditScore}</td>
				<td>${loan.risk}</td>
				<td>
					<button type="button" class="btn btn-info btn-sm"
						data-toggle="modal" data-target="#myModal${loan.loanID}">Details</button> 
						<!-- Modal -->
					<div class="modal fade" id="myModal${loan.loanID}" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Loan Details</h4>
								</div>
								<div class="modal-body">
									<p>
									<table class="table table-striped">
									<tr><td>Loan ID</td><td>${loan.loanID}</td><td>Amount</td><td>$${loan.amount}</td></tr>
									<tr><td>Borrower Name</td><td>${loan.borrowerName}</td><td>Monthly Payment</td><td>${loan.monthly_payment}</td></tr>
									<tr><td>Tax ID</td><td>${loan.taxID}</td><td>Interest Rate</td><td>${loan.interestRate}%</td></tr>
									<tr><td>Credit Score</td><td>${loan.creditScore}</td><td>Duration(months)</td><td>${loan.duration}</td></tr>
									<tr><td>Type</td><td>${loan.type}</td><td>Delinquency(months)</td><td>${loan.delinquency}</td></tr>
									<tr><td>Secured</td><td>${loan.secured}</td><td>Risk</td><td>${loan.risk}</td></tr>
									<tr><td>Issue Date</td><td>${loan.issue_date}</td><td></td><td></td></tr>
									<tr><td>Comments</td><td colspan="3">${loan.comments}</td></tr>
									</table>
									</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>
							</div>

						</div>
					</div>

				</td>
				<td><html:form action="purchase.do" method="POST">
						<html:hidden property="loanID" value="${loan.loanID }" />
						<input type="submit" name="method" value="purchase">
					</html:form></td>
			</tr>
		</c:forEach>
	</table>
</div>