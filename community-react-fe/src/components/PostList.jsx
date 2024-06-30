import "../style/components/PostList.css"

import { useNavigate } from "react-router-dom"
import { PurpleShortBtn } from "../components/Button"

import PostItem from "./PostItem"

function PostList({data}) {
    const nav = useNavigate()
    return (
        <div className="PostList">
            <div className="button-wrapper">
                <PurpleShortBtn ButtonName="게시글 작성" onClick={() => nav('/posts/new')} />
                <div className="list_wrapper">
                    {data.map((item) => <PostItem key={item.postId} {...item} />)}
                </div>
            </div>
        </div>
    )
}

export default PostList