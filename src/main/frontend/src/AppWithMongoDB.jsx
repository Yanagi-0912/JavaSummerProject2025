import { useState } from "react";
import "./App.css";

function App() {
  const [posts, setPosts] = useState([]);
  const [openIdx, setOpenIdx] = useState(null);

  const fetchPosts = (Zone) => {
    const encodedZone = encodeURIComponent(Zone);
    const url = `/api/sights/keelung?zone=${encodedZone}`;
    fetch(url)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
        setPosts(data);
        setOpenIdx(null);
      })
      .catch((error) => {
        console.error("從 MongoDB 獲取數據時出錯:", error);
        setPosts([]);
      });
  };

  const zones = ["中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖"];

  return (
    <article>
      <h1 className="text-3xl font-bold mb-6 text-white-700">基隆旅遊景點前端</h1>
      <div className="mb-6 flex flex-wrap gap-2 justify-center">
        {zones.map((zone) => (
          <button
            key={zone}
            onClick={() => fetchPosts(zone)}
            className="bg-gray-500 text-white px-4 py-2 rounded shadow hover:border transition duration-300 mb-2"
          >
            {zone}區
          </button>
        ))}
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {posts.map((post, idx) => (
          <div key={post.sightName} className="bg-gray-800 rounded-xl shadow-lg hover:shadow-2xl transition p-6 flex flex-col h-full">
            <div className="font-bold text-xl text-white mb-2">{post.sightName}</div>
            <div className="text-gray-300 text-base mb-3">
              <span className="font-semibold">類別:</span> {post.category}<br />
              <span className="font-semibold">區域:</span> {post.zone}<br />
              <a
                href={`https://www.google.com/maps/place/${post.address}`}
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-300 hover:text-blue-100 underline"
              >
                地址
              </a>
              : {post.address}
            </div>
            <button
              className="bg-gray-700 text-white px-4 py-2 rounded hover:bg-gray-600 hover:border-white hover:border transition duration-300 mb-2"
              onClick={() => setOpenIdx(openIdx === idx ? null : idx)}
            >
              詳細資料
            </button>
            {openIdx === idx && (
              <div className="mt-2 text-gray-300">
                {post.description}<br />
                <img
                  src={post.photoURL == "" ? "https://clipart-library.com/image_gallery/515127.jpg" : post.photoURL}
                  alt={post.sightName}
                  className="max-w-full rounded-lg mt-2 shadow"
                />
              </div>
            )}
          </div>
        ))}
      </div>
    </article>
  );
}

export default App;