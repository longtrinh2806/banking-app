const transactType = document.querySelector("#transact-type");
const paymentCard = document.querySelector(".payment-card");
const transferCard = document.querySelector(".transfer-card");
const depositCard = document.querySelector(".deposit-card");
const withdrawCard = document.querySelector(".withdraw-card");

transactType.addEventListener("change", () => {
    switch (transactType.value) {
        case "payment":
            paymentCard.style.display = "block";
            paymentCard.nextElementSibling.style.display = "none";
            depositCard.style.display = "none";
            withdrawCard.style.display = "none";
            break;
        case "deposit":
            depositCard.previousElementSibling.style.display = "none";
            depositCard.style.display = "block";
            depositCard.nextElementSibling.style.display = "none";
            paymentCard.style.display = "none";
            break;
        case "withdraw":
            withdrawCard.previousElementSibling.style.display = "none";
            withdrawCard.style.display = "block";
            paymentCard.style.display = "none";
            break;
    }
})

// Script xử lí dashboard: Tên người dùng, Tài khoản và số dư.

document.addEventListener("DOMContentLoaded", function () {
    // Lấy token từ Local Storage
    const token = localStorage.getItem('token');

    // Modal hiển thị cảnh báo đăng nhập
    const loginPromptModal = new bootstrap.Modal(document.getElementById('loginPromptModal'));

    // Nếu không tìm thấy token trong Local Storage, hiển thị modal cảnh báo và ẩn trước khi chuyển hướng
    if (!token) {
        console.error('Token not found in Local Storage');
        loginPromptModal.show();
        return;
    }

    // Gọi API để lấy thông tin tên người dùng và số dư tài khoản
    fetch("http://localhost:8080/api/dashboard", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}` // Sử dụng token từ Local Storage
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const nameAccount = data.nameAccount;
            const totalBalance = data.totalBalance;
            const userAccounts = data.getUserAccount;


            // Cập nhật tên người dùng và số dư tài khoản trong các phần tử HTML tương ứng
            document.getElementById("user-name").innerText = nameAccount;
            document.getElementById("total-balance").innerText = totalBalance;

            // Kiểm tra nếu có tài khoản, ẩn phần "No Register Account", ngược lại hiển thị nó
            const noAccountsContainer = document.getElementById("no-accounts-container");
            const accordionContainer = document.getElementById("accordionContainer");

            if (userAccounts && userAccounts.length > 0) {
                noAccountsContainer.style.display = "none";
                accordionContainer.style.display = "block";
            } else {
                noAccountsContainer.style.display = "block";
                accordionContainer.style.display = "none";
            }
        })
        .catch(error => {
            console.error("Error:", error);
            loginPromptModal.show();
        });
});

// Script xử lí add account
document.getElementById('add-account-btn').addEventListener('click', function () {
    var accountNumber = document.getElementById('accountNumberInput').value;
    var trimmedAccountNumber = accountNumber.trim();
    if (!trimmedAccountNumber) {
        // Hiển thị thông báo lỗi nếu trường tài khoản trống
        var errorMessage = document.getElementById('error-message');
        errorMessage.innerText = 'Please enter an account number.';
        return; // Ngăn việc gửi yêu cầu nếu trường tài khoản trống
    }

    var token = localStorage.getItem('token');
    fetch('http://localhost:8080/api/account', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            accountNumber: trimmedAccountNumber
            // Thêm các thông tin khác cần thiết cho request body nếu có
        })
    })
        .then(response => response.json())
        .then(data => {
            // Xử lý phản hồi từ API (nếu cần)
            console.log(data);
            alert(data.message);
            window.location.href = 'dashboard.html';
        })
        .catch(error => {
            // Xử lý lỗi (nếu có)
            console.error('Error:', error);
            alert(data.message);
            window.location.href = 'dashboard.html'
        });
});

document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('Token not found in Local Storage');
        return;
    }

    fetchUserAccountsAndPopulateElements(token);
    handleDeposit();
});

function fetchUserAccountsAndPopulateElements(token) {
    fetch("http://localhost:8080/api/dashboard", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const userAccounts = data.getUserAccount || [];
            populateAccordionWithAccounts(userAccounts);
            populateAccountSelect(userAccounts);
            populateDepositAccountSelect(userAccounts);
        })
        .catch(error => {
            console.error("Error:", error);
            // Xử lý lỗi ở đây nếu cần
        });
}

function populateAccordionWithAccounts(userAccounts) {
    const accordionContainer = document.getElementById('accordionFlushExample');
    accordionContainer.innerHTML = '';

    userAccounts.forEach((account, index) => {
        const { accountNumber, accountName, balance, createdAt } = account;
        const accordionItem = createAccordionItem(index + 1, accountNumber, accountName, balance, createdAt);
        accordionContainer.appendChild(accordionItem);
    });

    const noAccountsContainer = document.getElementById("no-accounts-container");

    if (userAccounts.length > 0) {
        noAccountsContainer.style.display = "none";
    } else {
        noAccountsContainer.style.display = "block";
    }
}

function createAccordionItem(id, accountNumber, accountName, balance, createdAt) {
    const accordionItem = document.createElement('div');
    accordionItem.classList.add('accordion-item');

    const header = document.createElement('h2');
    header.classList.add('accordion-header');

    const button = document.createElement('button');
    button.classList.add('accordion-button', 'collapsed');
    button.type = 'button';
    button.dataset.bsToggle = 'collapse';
    button.dataset.bsTarget = `#collapse-${id}`;
    button.setAttribute('aria-expanded', 'false');
    button.setAttribute('aria-controls', `collapse-${id}`);
    button.innerHTML = `Account:&nbsp;&nbsp;&nbsp; <strong> ${accountNumber}</strong>`;

    header.appendChild(button);

    const collapse = document.createElement('div');
    collapse.id = `collapse-${id}`;
    collapse.classList.add('accordion-collapse', 'collapse');
    collapse.dataset.bsParent = '#accordionFlushExample';

    const body = document.createElement('div');
    body.classList.add('accordion-body');

    const accountDetails = `
        <p><strong>Account Number:</strong> ${accountNumber}</p>
        <hr>
        <p><strong>Account Name:</strong> ${accountName}</p>
        <hr>
        <p><strong>Balance:</strong> ${balance}</p>
        <hr>
        <p><strong>Created At:</strong> ${createdAt}</p>
    `;
    body.innerHTML = accountDetails;

    collapse.appendChild(body);

    accordionItem.appendChild(header);
    accordionItem.appendChild(collapse);

    return accordionItem;
}

function populateAccountSelect(userAccounts) {
    const selectElement = document.querySelectorAll('select[name="account_id"]');

    selectElement.forEach(select => {
        // Xóa tất cả các options hiện có trong select
        select.innerHTML = '<option value="">-- Select Account --</option>';

        userAccounts.forEach(account => {
            const option = document.createElement('option');
            option.value = account.accountNumber;
            option.textContent = `${account.accountName} - ${account.accountNumber}`;
            select.appendChild(option);
        });
    });
}

// Script nạp tiền
function handleDeposit() {
    const depositButton = document.getElementById('deposit-btn');

    depositButton.addEventListener('click', function (event) {
        event.preventDefault();

        const depositAmount = document.querySelector('input[name="deposit_amount"]').value;
        const accountNumber = document.querySelector('select[name="deposit_account_id"]').value;
        const token = localStorage.getItem('token');

        const depositData = {
            depositAmount: depositAmount,
            accountNumber: accountNumber
        };

        fetch('http://localhost:8080/api/deposit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(depositData)
        })
            .then(response => {
                if (response.ok) {
                    // Hiển thị modal khi nạp tiền thành công
                    const depositSuccessModal = new bootstrap.Modal(document.getElementById('depositSuccessModal'));
                    depositSuccessModal.show();
                    setTimeout(function () {
                        depositSuccessModal.hide(); // Đóng modal
                        window.location.href = 'dashboard.html';
                    }, 4000);

                    return response.json();

                }
                throw new Error('Nạp tiền thất bại');
            })
            .then(data => {
                // Xử lý sau khi nạp tiền thành công
                // Có thể thêm xử lý dữ liệu ở đây nếu cần thiết
            })
            .catch(error => {
                console.error('Đã xảy ra lỗi khi nạp tiền:', error);
            });
    });
}


function populateDepositAccountSelect(userAccounts) {
    const depositSelect = document.querySelector('select[name="deposit_account_id"]');
    depositSelect.innerHTML = '<option value="">-- Select Account --</option>';

    userAccounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.accountNumber;
        option.textContent = `${account.accountName} - ${account.accountNumber}`;
        depositSelect.appendChild(option);
    });
}



// Script xử lí log out
document.getElementById("signOutButton").addEventListener("click", function (event) {
    event.preventDefault();

    // Xóa token khỏi Local Storage
    localStorage.removeItem("token");

    // Chuyển hướng người dùng đến trang đăng nhập hoặc trang chính
    window.location.href = "login.html"; // hoặc đến trang chính của ứng dụng của bạn
});
