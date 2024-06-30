import user1 from "./../assets/users/user1.png"
import user2 from "./../assets/users/user2.png"
import user3 from "./../assets/users/user3.png"
import post1 from "./../assets/posts/post1.png"
import post2 from "./../assets/posts/post2.jpg"
import post3 from "./../assets/posts/post3.png"

export function getUserImage(userId) {
    switch (userId) {
        case 1: return user1
        case 2: return user2
        case 3: return user3
        default: return null
    }
}

export function getPostImage(postId) {
    switch (postId) {
        case 1: return post1
        case 2: return post2
        case 3: return post3
        default: return null
    }
}