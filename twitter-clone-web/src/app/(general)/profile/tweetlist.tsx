'use client'

import React, { useState, useEffect, FormEvent } from 'react';
import { Button } from 'react-bootstrap';
import { useRouter } from 'next/navigation';
import Tweet from '../tweet';
import axios from 'axios';

type Tweet = {
  id: number; // Assuming there's an ID field; adjust as necessary
  content: string;
  // Add other fields if needed
};

export const TweetList = () => {

  const [tweets, setTweets] = useState<Tweet[]>([]);
  const [newTweet, setNewTweet] = useState<string>('');
  const [error, setError] = useState<string>('');
  const router = useRouter();

  useEffect(() => {
    fetchTweets();
  }, [])

  const getCookie = (name: string): string | undefined => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop()?.split(';').shift();
    return undefined;
  };

  const fetchTweets = async () => {
    const jwt = getCookie('jwt');
    // try {
    //   const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/tweets/all`, {
    //     credentials: 'include'
    //   })
    //   if (!response.ok) {
    //     throw new Error('Failed to fetch tweets');
    //   }
    //   const data = await response.json();
    //   setTweets(data);

    // } catch (error: any) {
    //   setError(error.message);
    // }
    try {
      const response = await axios.get(`${process.env.NEXT_PUBLIC_API_URL}/api/tweets/me`,
        {
          headers: {
            'Content-Type': 'application/json'
          },
          withCredentials: true
        }
      );
      if (response.status !== 200) {
        throw new Error('Failed to fetch tweets');
      }
      const data = response.data;
      setTweets(data);
    } catch (error: any) {
      setError(error);
    }
  }

  const handleTweetSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError('');
    try {
      // const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/tweets/create`, {
      //   method: "POST",
      //   headers: {
      //     'Content-Type': 'application/json'
      //   },
      //   body: JSON.stringify({ content: newTweet })
      // });
      // console.log(res)
      // if (res.ok) {
      //   console.log('OK');
      //   // router.push('/');
      // } else {
      //   console.log('Error occurred.');
      //   setError('Error occurred');
      // }
      const params = JSON.stringify({
        content: newTweet,
      });
      const res = await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/api/tweets/create`,
        params,
        {
          headers: {
            'Content-Type': 'application/json'
          },
          withCredentials: true
        }
      );
      console.log(res);
      if (res.status === 200) {
        console.log('OK');
        setNewTweet('');
        fetchTweets();
      }
    } catch (error) {
      console.log('Catch');
      console.log(error);
      setError('Catch')
    }
  }

  const handleTweetChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setNewTweet(e.target.value);
  };

  return (
    <div>
      <h3 className="text-xl font-bold mb-6">My Tweets</h3>
      <div className='' style={{ paddingTop: '40px' }}>
        {tweets.length === 0 && <p>No tweets available</p>}
        {tweets.map((tweet, index) => (
          <Tweet key={index} content={tweet.content} />
        ))}
      </div>
    </div>
  )

}