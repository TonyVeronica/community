import "../style/components/PostForm.css"
import { PurpleLongBtn } from "./Button"

function PostForm({FormTitle}) {
    return (
        <form className="PostForm">
            <div className="Form-title">{FormTitle}</div>
            <div className="title-section">
                <h4>제목</h4>
                <input placeholder="제목을 입력해주세요. (최대 26글자)" maxLength="26"></input>
            </div>
            <div className="content-section">
                <h4>내용</h4>
                <textarea placeholder="내용을 입력해주세요."></textarea>
            </div>
            <div className="helper-text">* helper text</div>
            <div className="image-section">
                <h4>이미지</h4>
                <input type="file" accept=".jpg, .jpeg, .png"></input>
            </div>
            <div className="button-section">
                <PurpleLongBtn ButtonName="완료"/>
            </div>
        </form>
    )
}

export default PostForm