import React, { createContext, useState, useEffect, useContext } from 'react';
import { db } from "../firebase/config";
import {
  collection,
  query,
  where,
  orderBy,
  onSnapshot,
} from "firebase/firestore";
import cookie from "react-cookies";
import MyContext from './MyContext';
import Apis, { authApi, endpoints } from "../utils/Apis";
export const NotisContext = createContext();

export const NotisProvider = ({ children }) => {
  const [notis, setNotis] = useState([]);
  const [loading, setLoading] = useState(false);
  const [user] = useContext(MyContext)

  const loadNotis = async () => {
    try {
      setLoading(true);
      const notisRef = collection(db, `notis`);
      const notis = query(notisRef,where("userId", "==", user.id),orderBy('createdAt','desc'));

      const unsubscribe = onSnapshot(notis, (snapshot) => {
        const newNotis = [];
        snapshot.forEach((doc) => {
          const data = doc.data();
          newNotis.push({
            id: doc.id,
            content: data.content,
            from: data.fromId,
            createdAt: data.createdAt.toDate(),
            is_read: data.is_read,
            userId: data.userId
          });
        });
        setNotis(newNotis);
      });
      return unsubscribe;
    } catch (error) {
      console.error("Error loading notis: ", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    setLoading(true);
    const timer = setTimeout(() => {
      loadNotis();
    }, 500); 
    return () => clearTimeout(timer);
  }, []);

  return (
    <NotisContext.Provider value={{ notis, loading }}>
      {children}
    </NotisContext.Provider>
  );
};
