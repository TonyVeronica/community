import Header from "../components/Header"
import InfoInput from "../components/InfoInput"
import PostForm from "../components/PostForm"
import { useNavigate } from "react-router-dom"

function NewPost() {
    const nav = useNavigate()

    const handleFormSubmit = (e) => {
        e.preventDefault()
    }

    return (
        <>
            <Header showBackButton={true} showCircleButton={true} nav={nav} />
            <PostForm FormTitle="게시글 수정" onSubmit={handleFormSubmit} />            
        </>
    )
}

export default NewPost