const Comments = ({ comment }: CommentProps) => {
  return (
      <>
        <div
          key={comment.id}
          className='w-full bg-white rounded-lg overflow-hidden shadow-lg'
        >
          <div className='px-6 py-4'>
                  <p className='text-gray-700 text-base'>{comment.text}</p>
                  <p className='text-gray-700 text-base'>By: {comment.user}</p>
                  <p className='text-gray-700 text-base'>Created: {comment.created_at}</p>
          </div>
        </div>
        </>
  );
};

export default Comments;
