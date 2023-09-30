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