import { FetchData } from '../../utils/FetchData';

interface Comment extends CommentProps {
  comments: any;
  setComments: any;
}

const Comments = ({ comment, comments, setComments }: Comment) => {
  const handleDelete = async () => {
    try {
      const res = await FetchData(`/api/v1/comments/${comment.id}`, 'DELETE');
    
      if (!res.ok) {
        throw new Error('Something went wrong');
      }

      const newComments = comments.filter(
        (c: any) => c.id !== comment.id
      );
   
      setComments(newComments);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div
        key={comment.id}
        className='w-full bg-white rounded-lg overflow-hidden shadow-lg'
      >
        <div className='px-6 py-4 relative'>
          <i
            onClick={handleDelete}
            className='absolute text-red-500 cursor-pointer top-3 text-xl right-5 fa-solid fa-trash'
          ></i>
          <p className='text-gray-700 text-base'>{comment.text}</p>
          <p className='text-gray-700 text-base'>By: {comment.user}</p>
          <p className='text-gray-700 text-base'>
            Created: {comment.created_at}
          </p>
        </div>
      </div>
    </>
  );
};

export default Comments;
