import React from 'react';

function AboutUs() {
  return (
    <div className='w-full bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <h1 className='text-4xl font-bold mb-8 text-blue-900'>About Us</h1>
      <div className='flex flex-wrap -mx-4'>
        <div className='w-full md:w-1/2 px-4 mb-8'>
          <img
            src='https://images.unsplash.com/photo-1555066931-4365d14bab8c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2670&q=80'
            alt='Company Logo'
            className='w-full mb-4 rounded-lg'
          />
          <p className='text-lg mb-4'>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer
            fermentum molestie nunc, ac commodo lacus auctor sed. Nullam at
            commodo felis. Nullam rutrum elit elit, vel elementum turpis
            consequat eu. Vestibulum id mi vel justo congue mattis. Praesent
            commodo libero eget velit fringilla, ac fermentum lacus luctus.
            Donec nec volutpat leo, ac dignissim massa. Sed at urna sit amet
            sapien facilisis lacinia.
          </p>
          <p className='text-lg mb-4'>
            In hac habitasse platea dictumst. Pellentesque eu urna ac risus
            egestas aliquet sed eget nunc. Vestibulum ante ipsum primis in
            faucibus orci luctus et ultrices posuere cubilia curae; Quisque sit
            amet nulla id ipsum dictum consectetur. Integer posuere aliquam
            elit, sed molestie ipsum suscipit eget. Sed malesuada lectus nec
            lectus porttitor interdum. Donec vitae ante at elit consectetur
            blandit non vel sapien.{' '}
          </p>
        </div>
        <div className='w-full md:w-1/2 px-4 mb-8'>
          <h2 className='text-2xl font-bold mb-4 text-blue-900'>Our Mission</h2>
          <p className='text-lg mb-4'>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer
            fermentum molestie nunc, ac commodo lacus auctor sed. Nullam at
            commodo felis. Nullam rutrum elit elit, vel elementum turpis
            consequat eu. Vestibulum id mi vel justo congue mattis. Praesent
            commodo libero eget velit fringilla, ac fermentum lacus luctus.
            Donec nec volutpat leo, ac dignissim massa. Sed at urna sit amet
            sapien facilisis lacinia.
          </p>
          <h2 className='text-2xl font-bold mb- text-blue-900'>Our Vision</h2>
          <p className='text-lg mb-4'>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer
            fermentum molestie nunc, ac commodo lacus auctor sed. Nullam at
            commodo felis. Nullam rutrum elit elit, vel elementum turpis
            consequat eu. Vestibulum id mi vel justo congue mattis. Praesent
            commodo libero eget velit fringilla, ac fermentum lacus luctus.
            Donec nec volutpat leo, ac dignissim massa. Sed at urna sit amet
            sapien facilisis lacinia.
          </p>
        </div>
      </div>
    </div>
  );
}

export default AboutUs;
