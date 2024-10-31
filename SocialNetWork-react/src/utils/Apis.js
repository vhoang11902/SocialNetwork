import axios from "axios";
import cookie from "react-cookies";

const SERVER_CONTEXT = "/SocialNetwork";
const SERVER = "http://localhost:8080";

export const endpoints = {
    // AUTH
    "login": `${SERVER_CONTEXT}/api/login/`,
    "current-user": `${SERVER_CONTEXT}/api/current-user/`,
    "register": `${SERVER_CONTEXT}/api/users/`,
    // CONNECTION
    "profile": (id)=>`${SERVER_CONTEXT}/api/user/${id}/`,
    "friends": `${SERVER_CONTEXT}/api/actConnect/`,
    "sendRequest":(id)=>`${SERVER_CONTEXT}/api/connections/${id}/`,
    "acceptRequest":(id)=>`${SERVER_CONTEXT}/api/connections/${id}/accept`,
    "deleteFriend":(id)=>`${SERVER_CONTEXT}/api/connections/${id}/delete`,
    "requestReceiptList": `${SERVER_CONTEXT}/api/reqReceipt/`,
    "requestSendList": `${SERVER_CONTEXT}/api/reqSend/`,
    //POST
    "add-post": `${SERVER_CONTEXT}/api/posts/`,
    "posts": (pageNumber) => `${SERVER_CONTEXT}/api/posts?pageNumber=${pageNumber}`,
    "userPosts": (userId) => `${SERVER_CONTEXT}/api/posts?userId=${userId}`,

    // REACTION

    "add-reaction": (postId,action) =>`${SERVER_CONTEXT}/api/posts/${postId}/reactions/?action=${action}`,
    "update-reaction": (postId,reactionId,action) =>`${SERVER_CONTEXT}/api/posts/${postId}/reactions/${reactionId}/?action=${action}`,
    "delete-reaction": (postId,reactionId) =>`${SERVER_CONTEXT}/api/posts/${postId}/reactions/${reactionId}`,
    //COMMENT
    "add-comment": `${SERVER_CONTEXT}/api/comments/`,
    "comments": (postId) => `${SERVER_CONTEXT}/api/posts/${postId}/comments`,
    "delete-comment": (commentId) => `${SERVER_CONTEXT}/api/comments/${commentId}`,
    
}

export const authApi = () => {
    return axios.create({
        baseURL: SERVER,
        headers: {
            "Authorization":  cookie.load("token")
        }
    })
}

export default axios.create({
    baseURL: SERVER
})