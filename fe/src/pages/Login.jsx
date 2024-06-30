import "../style/pages/Login.css"
import Header from "../components/Header"
import InfoInput from "../components/InfoInput"
import { PurpleLongBtn, NoStyleButton } from "../components/Button"
import { useNavigate } from "react-router-dom"
import { validateEmail, validatePassword, setHelperMsgAndStyle } from "../util/validator"
import useValidation from "../hooks/useValidation";
import { useState } from "react"

function Login() {
    const nav = useNavigate()

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

    const handleFormSubmit = (e) => {
        e.preventDefault();
        
        if (isEmailValid && isPasswordValid) {
            console.log("로그인 완료");
            // 로그인 로직
            // fetch("URL", {
            //     method: "POST",
            //     headers: {
            //         "Content-Type": "application/json",
            //     },
            //     body: JSON.stringify({
            //         email: email,
            //         password: password,
            //     }),
            // })
            //     .then((response) => response.json())
            //     .then((data) => {
            //         // 서버 응답 처리
            //         console.log(data);
            //         if (data.success) {
            //             // 로그인 성공 처리
            //             nav('/posts');
            //         } else {
            //             // 로그인 실패 처리
            //             console.log("로그인 실패:", data.message);
            //         }
            //     })
            //     .catch((error) => {
            //         console.error("로그인 요청 중 오류 발생:", error);
            //     });

        } else {
            console.log("유효하지 않은 입력값이 있습니다.");
        }
    }

    return (
        <>
            <Header showBackButton={false} showCircleButton={false} />
            <form id="loginForm" onSubmit={handleFormSubmit}>
                <div className="title">로그인</div>
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
                <PurpleLongBtn ButtonName="로그인" />
            </form >
            <NoStyleButton ButtonName="회원가입" onClick={() => nav('/join')} />
        </>
    )
}

export default Login