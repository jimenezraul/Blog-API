import React from 'react';
import { Link } from 'react-router-dom';

interface ProfileCardProps {
  id: string;
  name: string;
  email: string;
  imageUrl: string;
  createdAt: string;
  updatedAt: string;
}

const ProfileCard = ({
  id,
  name,
  email,
  imageUrl,
  createdAt,
  updatedAt,
}: ProfileCardProps) => {
  return (
    <div className='relative max-w-sm mx-auto bg-white rounded-lg overflow-hidden shadow-lg'>
      <div className='p-6 bg-slate-300'>
      <div className='font-bold text-xl mb-2 text-blue-900'>{name}</div>
      <div className='absolute top-0 right-0 mt-4 mr-4'>
        <Link to={`/edit-profile/${id}`}>
          <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>
            Edit
          </button>
        </Link>
        </div>
      </div>
      <div className='px-6 py-4'>
        <p className='text-gray-700 text-base'>Email: <span className='font-bold'>{email}</span></p>
        <p className='text-gray-700 text-base'>Created: <span className='font-bold'>{createdAt}</span></p>
        <p className='text-gray-700 text-base'>Updated:  <span className='font-bold'>{updatedAt}</span></p>
      </div>
    </div>
  );
};

export default ProfileCard;
