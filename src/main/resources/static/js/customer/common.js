function checkDuplicateUsername() {
    var username = document.getElementById("username");
    var usernameValue = username.value;

    if (usernameValue == "") {
        alert("아이디를 입력해주세요");
        return
    }

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

    if (!checkEmailType()) {
        alert("이메일 형식을 맞춰주세요.");
        return;
    }

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

function checkEmailType() {
    var email = document.getElementById("email");
    var emailValue = email.value;
    var emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

    if(!emailRegex.test(emailValue)) {
        return false;
    }

    return true;
}

function findCheck() {
    if (!checkEmailType()) {
        alert("이메일 형식을 맞춰주세요!");
        return
    } else {
        document.getElementById("findForm").submit();
    }
}