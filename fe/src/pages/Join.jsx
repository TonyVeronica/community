import "../style/pages/Join.css";
import Header from "../components/Header";
import InfoInput from "../components/InfoInput";
import { PurpleLongBtn, NoStyleButton } from "../components/Button";
import { useNavigate } from "react-router-dom";
import { validateEmail, validatePassword, validateConfirmPassword, validateNickname } from "../util/validator";
import useValidation from "../hooks/useValidation";
import { useState } from "react";

function Join() {
    const nav = useNavigate();
    const [imageUrl, setImageUrl] = useState(null);

    const {
        value: email,
        helperMsg: emailHelperMsg,
        helperStyle: emailHelperStyle,
        isValid: isEmailValid,
        handleChange: handleEmailChange
    } = useValidation("", validateEmail);

    const {
        value: password,
        helperMsg: passwordHelperMsg,
        helperStyle: passwordHelperStyle,
        isValid: isPasswordValid,
        handleChange: handlePasswordChange
    } = useValidation("", validatePassword);

    const {
        value: confirmPassword,
        helperMsg: confirmPasswordHelperMsg,
        helperStyle: confirmPasswordHelperStyle,
        isValid: isConfirmPasswordValid,
        handleChange: handleConfirmPasswordChange
    } = useValidation("", value => validateConfirmPassword(password, value));

    const {
        value: nickname,
        helperMsg: nicknameHelperMsg,
        helperStyle: nicknameHelperStyle,
        isValid: isNicknameValid,
        handleChange: handleNicknameChange
    } = useValidation("", validateNickname);

    const handleFormSubmit = (e) => {
        e.preventDefault();

        if (isEmailValid && isPasswordValid && isConfirmPasswordValid && isNicknameValid) {
            console.log("회원가입 완료");
            // 회원가입 로직
        } else {
            console.log("유효하지 않은 입력값이 있습니다.");
        }
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setImageUrl(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    return (
        <>
            <Header showBackButton={true} showCircleButton={false} nav={nav} />
            <form id="joinForm" onSubmit={handleFormSubmit}>
                <div className="title">회원가입</div>
                <div className="InputBox">
                    <div className="InputBoxTitle">프로필 사진</div>
                    <div className="prf-img">
                        <input
                            className="image"
                            id="fileUpload"
                            type="file"
                            accept=".jpg,.png"
                            name="userPicture"
                            onChange={handleImageChange}
                        />
                        <label
                            id="fileUpload-label"
                            htmlFor="fileUpload"
                            className={imageUrl ? 'has-image' : ''}
                            style={imageUrl ? { backgroundImage: `url(${imageUrl})` } : {}}
                        ></label>
                    </div>
                </div>
                <InfoInput
                    title="이메일"
                    type="email"
                    placeholder="이메일을 입력하세요"
                    value={email}
                    onChange={handleEmailChange}
                    helperMsg={emailHelperMsg}
                    helperStyle={emailHelperStyle}
                />
                <InfoInput
                    title="비밀번호"
                    type="password"
                    placeholder="비밀번호를 입력하세요"
                    value={password}
                    onChange={handlePasswordChange}
                    helperMsg={passwordHelperMsg}
                    helperStyle={passwordHelperStyle}
                />
                <InfoInput
                    title="비밀번호 확인"
                    type="password"
                    placeholder="비밀번호를 한 번 더 입력하세요"
                    value={confirmPassword}
                    onChange={handleConfirmPasswordChange}
                    helperMsg={confirmPasswordHelperMsg}
                    helperStyle={confirmPasswordHelperStyle}
                />
                <InfoInput
                    title="닉네임"
                    type="text"
                    placeholder="닉네임을 입력하세요"
                    value={nickname}
                    onChange={handleNicknameChange}
                    helperMsg={nicknameHelperMsg}
                    helperStyle={nicknameHelperStyle}
                />
                <PurpleLongBtn ButtonName="회원가입" />
            </form>
            <NoStyleButton ButtonName="로그인하러 가기" onClick={() => nav('/')} />
        </>
    );
}

export default Join;
