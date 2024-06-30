import React from 'react';
import "../style/components/Modal.css";

function Modal({ modalTitle, modalContent, onClose }) {
    return (
        <div className="Modal-overlay">
            <div className="Modal" onClick={(e) => e.stopPropagation()}>
                <div className="modal-title-section">{modalTitle}</div>
                <div className="modal-content-section">{modalContent}</div>
                <div className="modal-button-section">
                    <button className="no-button" onClick={onClose}>취소</button>
                    <button className="ok-button">확인</button>
                </div>
            </div>
        </div>
    );
}

export default Modal;
