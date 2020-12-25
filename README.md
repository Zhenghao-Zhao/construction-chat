# construction-chat
A peer-to-peer chat application made for SignOnSite in late 2018. 

## Goal
The goal of this application is to lessen the communication overhead within a construction site and help site managers organize and communicate with the workers.

## Overview
![application screenshots](https://github.com/Zhenghao-Zhao/construction-chat/blob/HomePage_Zhenghao/cc_screenshot.png)

## Home page
The home page consists of a contact list of managers and workers of the user, indicated by their profile pictures W (stands for Worker) and M (stands for Manager). 
In the contact list, there is also a bright yellow dot or a grey dot next to the name to indicate if the person is available. You can also find all managers or all worker
by swiping to either the Manager view or Worker view.

## Chat Archive
The chat archieve page stores the history of all chats of the user. The user can go back to chatting by tapping on an item of the list. The chats will be sorted in a 
chronological order, meaning the most recent chats will be at the top of the list.   

## Mesaging page
The messaging page shows all messages the user has exchanged with another user. It has a commonly used UI as with many other chat applications, with receiver's messages appearing 
on the left and sender's on the right and the name of the receiver appearing at the top.

## Backend
We use Firebase for storing messages, user profiles and other application data. 
