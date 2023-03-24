import React from 'react';

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
    <div className='max-w-sm mx-auto bg-white rounded-lg overflow-hidden shadow-lg'>
      <img className='w-full' src={imageUrl} alt={name} />
      <div className='px-6 py-4'>
        <div className='font-bold text-xl mb-2'>{name}</div>
        <p className='text-gray-700 text-base'>Email: {email}</p>
        <p className='text-gray-700 text-base'>Created: {createdAt}</p>
        <p className='text-gray-700 text-base'>Updated: {updatedAt}</p>
      </div>
    </div>
  );
};

export default ProfileCard;
