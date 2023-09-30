function join() {
    const name = document.getElementById("name").value;
    const username = document.getElementById("username").value;
    const pw = document.getElementById("pw").value;
    const pwConfirm = document.getElementById("pwConfirm").value;
    const email = document.getElementById("email").value;
    const address = document.getElementById("address").value;

    if (name == "") {
        alert("이름을 작성해주세요.");
        return;
    }

    if (username == "") {
        alert("아이디를 작성해주세요.");
        return;
    }

    if (pw == "") {
        alert("비밀번호를 작성해주세요.");
        return;
    }

    if (pw != pwConfirm) {
        alert("비밀번호가 맞지 않습니다.")
        return;
    }

    if (email == "") {
        alert("이메일을 작성해주세요.");
        return;
    }

    if(address == "") {
        alert("주소를 작성해주세요,");
        return;
    }

    document.getElementById("joinForm").submit();
    alert("회원가입이 완료되었습니다.");
}

window.addEventListener('load', () => {
    const forms = document.getElementsByClassName('validation-form');

    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);
    });
}, false);
