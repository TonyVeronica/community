import "../style/components/PostItem.css"

import { useNavigate } from "react-router-dom"
import { getUserImage } from "../util/get-image.js"
import { getPostImage } from "../util/get-image.js"

function PostItem({ postId, userId, nickname, userImage, title, content, postImage, created_at, updated_at, delete: isDeleted, view, like, comment_count }) {
    const nav = useNavigate()
    return (
        <div className="PostItem" onClick={() => nav(`/posts/${postId}`)}>
            <div className="postTitle">{title}</div>
            <div className="meta_and_date">
                <div className="meta">
                    <span>좋아요 {like}</span>
                    <span>댓글수 {comment_count}</span>
                    <span>조회수 {view}</span>
                </div>
                <div className="date">{created_at}</div>   
            </div>
            <hr />
            <div className="user">
                <div className="userImage">
                    <img src={getUserImage(userId)}></img>
                </div>
                <div className="nickname">{nickname}</div>
            </div>
        </div>
    )
}

export default PostItem
