function checkDuplicateUsername() {
    var username = document.getElementById("username");
    var usernameValue = username.value;

    $.ajax({
        url : "checkUsername",
        data : {
            "param" : usernameValue
        },
        success : function rstData(data) {
            console.log(data)
            if(data.id != null) {
                alert("즁복된 아이디가 있습니다");
                emailValue = "";
            } else {
                alert("사용 가능한 아이디입니다!");
            }
        }
    })
}

function checkDuplicateEmail() {
    var email = document.getElementById("email");
    var emailValue = email.value;
    $.ajax({
        url : "checkEmail",
        data : {
            "param" : emailValue
        },
        success : function rstData(data) {
            if(data.id != null) {
                alert("즁복된 이메일이 있습니다");
                emailValue = "";
            } else {
                alert("사용 가능한 이메일입니다!");
            }
        }
    })
}

function join() {
    const pw = document.getElementById("pw").value;
    const pwConfirm = document.getElementById("pwConfirm").value;

    if (pw != pwConfirm) {
        alert("비밀번호가 맞지 않습니다.")
        pw = ""
        pwConfirm = ""
    } else {
        document.getElementById("joinForm").submit();
        alert("회원가입이 완료되었습니다.")
    }
}