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
        case "transfer":
            transferCard.previousElementSibling.style.display = "none";
            transferCard.style.display = "block";
            transferCard.nextElementSibling.style.display = "none";
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
            transferCard.style.display = "none";
            paymentCard.style.display = "none";
            break;
    }
})

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
            const nameAccount = data.nameAccount; // Thay "userName" bằng trường chứa tên người dùng trong response từ API
            const totalBalance = data.totalBalance; // Thay "totalBalance" bằng trường chứa số dư tài khoản trong response từ API

            // Cập nhật tên người dùng và số dư tài khoản trong các phần tử HTML tương ứng
            document.getElementById("user-name").innerText = nameAccount;
            document.getElementById("total-balance").innerText = totalBalance;
        })
        .catch(error => {
            console.error("Error:", error);
            loginPromptModal.show();
        });
});
