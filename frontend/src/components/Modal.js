import React from 'react';
import Button from './Button';

import './Modal.css';

const Modal = ({ data, onClose }) => {
  if (!data) return null;

  return (
    <div id="overlay">
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h3>{data.title}</h3>
        <p>{data.description.split('\n').map((line, index) => (
            <React.Fragment key={index}>
              {line}
              <br />
            </React.Fragment>
          ))}
        </p>
        <p><Button onClick={onClose} className="modal-button">Close</Button></p>
      </div>
    </div>
  );
};

export default Modal;
