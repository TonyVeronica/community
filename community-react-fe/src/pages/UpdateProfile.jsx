import "../style/pages/Login.css"
import Header from "../components/Header"
import InfoInput from "../components/InfoInput"
import Modal from "../components/Modal"
import { PurpleLongBtn, NoStyleButton } from "../components/Button"
import { useNavigate } from "react-router-dom"
import { useState } from "react"

function UpdateProfile() {
    const [imageUrl, setImageUrl] = useState(null);
    const [isModalShow, setIsModalShow] = useState(false);

    const handleFormSubmit = (e) => {
        e.preventDefault()
    }

    const handleImageChange = (e) => {
        const file = e.target.files[0]
        if (file) {
            const reader = new FileReader()
            reader.onloadend = () => {
                setImageUrl(reader.result)
            }
            reader.readAsDataURL(file)
        }
    }

    const handleOpenModal = () => {
        setIsModalShow(true)
    }

    const handleCloseModal = () => {
        setIsModalShow(false)
    }

    return (
        <>
            <Header showBackButton={false} showCircleButton={true} />
            <form id="Form" onSubmit={handleFormSubmit}>
                <div className="title" style={{marginTop:"70px"}}>회원정보수정</div>
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
                <div className="email-section">
                    <div className="email-title"
                        style={{fontSize: "15px", fontWeight: "700", marginTop: "30px",marginBottom: "10px"}}>이메일</div>
                    <div className="email-content"
                        style={{marginBottom: "14px"}}>test@test.com</div>
                </div>
                <InfoInput title="닉네임" type="text" placeholder="스타트업코드" />
                <PurpleLongBtn ButtonName="수정하기" />
            </form >
            <NoStyleButton ButtonName="회원탈퇴" onClick={handleOpenModal} />
            {isModalShow && (
                <Modal
                    modalTitle="회원탈퇴 하시겠습니까?"
                    modalContent="작성된 게시글과 댓글은 삭제됩니다."
                    onClose={handleCloseModal}
                />    
            )} 
        </>
    )
}

export default UpdateProfile