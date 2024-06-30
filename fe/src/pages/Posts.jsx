import "../style/pages/Posts.css"

import Header from "../components/Header"

import PostList from "../components/PostList"
import postData from "../data/posts.js"
import { useState } from "react"

function Posts() {
    const [data, setData] = useState(postData)
    return (
        <>
            <Header showBackButton={false} showCircleButton={true} />
            <div className="wrapper">
                <div className="intro">안녕하세요,<br /> 아무 말 대잔치 <strong>게시판</strong>입니다.</div>
                <PostList data={data} />
            </div>
            
        </>
    )
}

export default Posts