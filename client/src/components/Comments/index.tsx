import { FetchData } from '../../utils/FetchData';
import Auth from '../../auth';

interface Comment extends CommentProps {
  comments: CommentProps[];
  setComments: any;
}

const Comments = ({ comment, comments, setComments }: Comment) => {
  const isAdmin = Auth.isAdmin();
  const userId = Auth.getUserId();
  const handleDelete = async () => {
    try {
      const res = await FetchData(`/api/v1/comments/${comment.id}`, 'DELETE');

      if (!res.ok) {
        throw new Error('Something went wrong');
      }

      const newComments = comments.filter((c: any) => c.id !== comment.id);

      setComments(newComments);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div
        key={comment.id}
        className='w-full max-w-3xl bg-white shadow-lg rounded-lg overflow-hidden'
      >
        <div className='relative flex text-white p-6 bg-slate-500'>
          <p className='text-base'>By: <span className='font-bold'>{comment.user}</span></p>
          <p className='text-base'>{comment.created_at}</p>
          {isAdmin || userId === comment?.userId ? (
            <i
              onClick={handleDelete}
              className='bg-slate-400 transition ease-in-out duration-300 hover:bg-slate-100 rounded-lg p-3 absolute text-red-500 hover:text-red-600 cursor-pointer top-3 text-xl right-5 fa-solid fa-trash'
            ></i>
          ) : null}
        </div>
        <div className='px-6 py-4 '>
          <h2 className='text-gray-700 text-xl font-semibold'>
            {comment.text}
          </h2>
        </div>
      </div>
    </>
  );
};

export default Comments;
