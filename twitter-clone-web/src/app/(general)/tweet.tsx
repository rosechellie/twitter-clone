// app/tweet.tsx
import React from 'react';

type TweetProps = {
  content: string;
};

const Tweet: React.FC<TweetProps> = ({ content }) => {
  return (
    <div style={{ border: '1px solid #ccc', padding: '10px', margin: '10px 0' }}>
      <p>{content}</p>
    </div>
  );
};

export default Tweet;