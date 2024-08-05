// components/Sidebar.tsx
import Link from 'next/link';
import './Sidebar.css'; // Import the module CSS

const Sidebar = () => {
  // return (
  //   <div className="w-64 h-screen bg-gray-100 fixed ">
  //   {/* <div className="w-64 h-screen bg-gray-100 text-white fixed"> */}
  //     {/* <div className="p-5"> */}
  //       <h1 className="text-2xl font-bold mb-6">Tweeter</h1>
  //       <ul>
  //         <li className="mb-4">
  //           <Link className='button' href="/home">
  //             {/* <a className="text-lg hover:text-gray-400"> */}
  //             Home
  //             {/* </a> */}
  //           </Link>
  //         </li>
  //         <li className="mb-4">
  //           <Link className='button' href="/profile">
  //             {/* <a className="text-lg hover:text-gray-400"> */}
  //               Profile
  //             {/* </a> */}
  //           </Link>
  //         </li>
  //       </ul>
  //     {/* </div> */}
  //   </div>
  // );
  return (
    <div className='sidebar-container'>
      <h1 className="text-2xl font-bold mb-4">Tweeter</h1>
      <Link className='button' href="/home">
        Home
      </Link>
      <Link className='button' href="/profile">
        Profile
      </Link>
    </div>
  );
};

export default Sidebar;