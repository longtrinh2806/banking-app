document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('Token not found');
        window.location.href = 'login.html';
        return;
    }

    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    };

    fetch('http://localhost:8080/api/payment', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch transaction history');
            }
            return response.json();
        })
        .then(data => {
            const transactionHistoryElement = document.getElementById('transactionHistory');
            const transactionHistoryHTML = createTransactionHistoryHTML(data);
            transactionHistoryElement.innerHTML = transactionHistoryHTML;
        })
        .catch(error => {
            console.error('Error fetching transaction history:', error);
        });
});

function createTransactionHistoryHTML(data) {
    let html = `
        <table>
            <tr>
                <th>Payment ID</th>
                <th>Account Name</th>
                <th>Account Number</th>
                <th>Beneficiary Name</th>
                <th>Beneficiary Account</th>
                <th>Amount</th>
                <th>Transaction Type</th>
            </tr>
    `;

    data.forEach(transaction => {
        html += `
            <tr>
                <td>${transaction.paymentId}</td>
                <td>${transaction.accountName}</td>
                <td>${transaction.accountNumber}</td>
                <td>${transaction.beneficiaryName}</td>
                <td>${transaction.beneficiaryAccount}</td>
                <td>${transaction.amount}</td>
                <td>${transaction.transactionType}</td>
            </tr>
        `;
    });

    html += '</table>';
    return html;
}
