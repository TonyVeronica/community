const EMAIL_REGEX = /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9-.]+$/;
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,20}/;

export const validateEmail = (email) => {
    if (!email) {
        return { isValid: false, message: "* 이메일을 입력해주세요." };
    } else if (!EMAIL_REGEX.test(email)) {
        return { isValid: false, message: "* 올바른 이메일 주소 형식을 입력해주세요. (예: 123@example.com)" };
    } else {
        return { isValid: true, message: "* 올바른 이메일 양식입니다." };
    }
};

export const validatePassword = (password) => {
    if (!password) {
        return { isValid: false, message: "* 비밀번호를 입력해주세요." };
    } else if (!PASSWORD_REGEX.test(password)) {
        return { isValid: false, message: "* 비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다." };
    } else {
        return { isValid: true, message: "* 올바른 비밀번호 양식입니다." };
    }
};

export const validateConfirmPassword = (password, confirmPassword) => {
    const isValid = password === confirmPassword;
    return {
        isValid: isValid,
        message: isValid ? "* 비밀번호가 일치합니다." : "* 비밀번호가 일치하지 않습니다.",
    }
}

export const validateNickname = (nickname) => {
    if (!nickname) {
        return { isValid: false, message: "* 닉네임을 입력해주세요." };
    } else if (/\s/.test(nickname)) {
        return { isValid: false, message: "* 띄어쓰기를 없애주세요." };
    } else if (nickname.length > 10) {
        return { isValid: false, message: "* 최대 10글자 까지 입니다." };
    } else {
        return { isValid: true, message: "* 올바른 닉네임 양식입니다." };
    }
};


export const setHelperMsgAndStyle = (setHelperMsg, setHelperStyle, validationResult) => {
    setHelperMsg(validationResult.message)
    setHelperStyle(validationResult.isValid ? "success" : "error");
};