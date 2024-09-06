import React from 'react';

const Button = ({ as: Component = 'button', href, children, ...props }) => {
    // "as"プロパティが'a'ならhrefを指定し、デフォルトは'button'を使う
    if (Component === 'a') {
        return (
            <a href={href} {...props}>
                {children}
            </a>
        );
    }

    return (
        <button {...props}>
            {children}
        </button>
    );
};

export default Button;
