import "../style/pages/Login.css"
import Header from "../components/Header"
import InfoInput from "../components/InfoInput"
import { PurpleLongBtn, NoStyleButton } from "../components/Button"
import { useNavigate } from "react-router-dom"

function UpdatePassword() {
    const nav = useNavigate()

    const handleFormSubmit = (e) => {
        e.preventDefault()
    }
    return (
        <>
            <Header showBackButton={false} showCircleButton={true} />
            <form id="Form" onSubmit={handleFormSubmit}>
                <div className="title">비밀번호 수정</div>
                <InfoInput title="비밀번호" type="password" placeholder="비밀번호를 입력하세요" />
                <InfoInput title="비밀번호 확인" type="password" placeholder="비밀번호를 한 번 더 입력하세요" />
                <PurpleLongBtn ButtonName="수정하기" />
            </form >
        </>
    )
}

export default UpdatePassword