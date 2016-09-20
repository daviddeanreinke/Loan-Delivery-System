<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main history">
	<div class="title">
	<table>
		<tr>
			<td><h2>Daily Commit History</h2></td>
			<td>&nbsp&nbsp ${sessionScope.date} &nbsp&nbsp</td>
			<td>Institution ID: ${sessionScope.instID}</td>
		</tr>
	</table>
	</div>
	<table class="approvedLoans" style="border-bottom: 1px solid black">
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>Amount</th>
			<th>Monthly<br>Payment
			</th>
			<th>Interest<br>Rate
			</th>
			<th>Credit<br>Score
			</th>
			<th>Risk</th>
			<th>Approved</th>
		</tr>
		<c:forEach items="${sessionScope.dailyCommittedLoans}" var="loan">
			<tr>
				<td>${loan.borrowerName}</td>
				<td>${loan.type}</td>
				<td>${loan.amount}</td>
				<td>${loan.monthly_payment}</td>
				<td>${loan.interestRate}</td>
				<td>${loan.creditScore}</td>
				<td>${loan.risk}</td>
				<td>${loan.approved}</td>
				<td>
				<c:choose>
					<c:when test="${loan.approved == false}">
					<button type="button" class="btn btn-warning btn-sm"
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
									<tr><td>Issue Date</td><td>${loan.issue_date}</td><td>Approved</td><td>${loan.approved}</td></tr>
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
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				
				</td>
			</tr>
		</c:forEach>
	</table>
</div>