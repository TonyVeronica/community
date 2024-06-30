import '../style/pages/DetailPost.css';

import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import postData from '../data/posts.js';
import Header from '../components/Header.jsx';
import Modal from '../components/Modal.jsx';
import {UpdateDeleteBtn, PurpleShortBtn} from '../components/Button.jsx';
import { getUserImage } from "../util/get-image.js"
import { getPostImage } from "../util/get-image.js"

function DetailPost() {
    const [isModalShow, setIsModalShow] = useState(false)
    const nav = useNavigate()
    const { id } = useParams();
    const post = postData.find((item) => item.postId === parseInt(id));

    if (!post) {
        return <div>게시물을 찾을 수 없습니다.</div>;
    }

    const handleOpenModal = () => {
        setIsModalShow(true)
    }

    const handleCloseModal = () => {
        setIsModalShow(false)
    }

    return (
        <>
            <Header showBackButton={true} showCircleButton={true} nav={nav} />
            <div className="DetailPost">
                <div className='post-title'>{post.title}</div>
                <div className='info-section'>
                    <div className='user-img'>
                        <img src={getUserImage(post.userId)}></img>
                    </div>
                    <div className='user-nickname'>{post.nickname}</div>
                    <div className='create-date'>{post.created_at}</div>
                    <div style={{marginLeft: "auto"}}>
                    <UpdateDeleteBtn onClick={handleOpenModal} />
                    {isModalShow && (
                        <Modal
                            modalTitle="게시글을 삭제하시겠습니까?"
                            modalContent="삭제한 내용은 복구할 수 없습니다."
                            onClose={handleCloseModal}
                        />    
                    )} 
                    </div>
                </div>
                <hr style={{border: "0.5px solid rgb(0,0,0,0.16)", width: "100%"}}/>
                <div className='post-img'>
                    <img src={getPostImage(post.postId)}></img>
                </div>
                <div className='post-content'>{post.content}</div>
                <div className='view-like-section'>
                    <div className='view'>{post.view}<br/>조회수</div>
                    <div className='like'>{post.like}<br/>좋아요</div>
                </div>
                <hr style={{ border: "0.5px solid rgb(0,0,0,0.16)", width: "100%" }} />
                <div className='comment-section'>
                    <textarea className='comment' placeholder='댓글을 남겨주세요'></textarea>
                    <div className='comment-button'>
                        <PurpleShortBtn ButtonName="댓글 등록"/>
                    </div>
                </div>
            </div>
            
        </>
    );
}

export default DetailPost;
