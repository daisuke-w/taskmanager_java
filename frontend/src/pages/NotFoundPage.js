import React, { useEffect } from 'react';
import Button from '../components/Button';

import '../components/TaskForm.css';

const NotFoundPage = () => {
    useEffect(() => {
        // エラー画面がインデックス登録されないようにする
        const metaTag = document.querySelector("meta[name='robots']");
        if (metaTag) {
            metaTag.setAttribute("content", "noindex");
        } else {
            // メタタグが存在しない場合は新しく作成する
            const newMetaTag = document.createElement('meta');
            newMetaTag.name = 'robots';
            newMetaTag.content = 'noindex';
            document.head.appendChild(newMetaTag);
        }
    }, []); // 空の依存配列でコンポーネントのマウント時のみ実行

    return (
        <div>
            <h1>404 Not Found</h1>
            <p>申し訳ありませんが、ページが見つかりませんでした。</p>
            <Button as="a" href="/" className="task-list-button">Task List</Button>
        </div>
    );
};

export default NotFoundPage;
