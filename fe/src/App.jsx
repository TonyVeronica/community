import "./App.css";
import { Routes, Route } from "react-router-dom"
import Login from "./pages/Login";
import Join from "./pages/Join";
import Posts from "./pages/Posts";
import NewPost from "./pages/NewPost";
import DetailPost from "./pages/DetailPost";
import UpdatePost from "./pages/UpdatePost";
import UpdateProfile from "./pages/UpdateProfile";
import UpdatePassword from "./pages/UpdatePassword";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <>
        <Routes>      
            <Route path="/" element={<Login />} />
            <Route path="/join" element={<Join />} />
            <Route path="/posts" element={<Posts />} />
            <Route path="/posts/new" element={<NewPost />} />
            <Route path="/posts/:id" element={<DetailPost />} />
            <Route path="/posts/:id/update" element={<UpdatePost />} />
            <Route path="/user/update" element={<UpdateProfile />} />
            <Route path="/user/password" element={<UpdatePassword />} />
            <Route path="*" element={<NotFound />} />
        </Routes>
          
    </>
  );
}

export default App;
