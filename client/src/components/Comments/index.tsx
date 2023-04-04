import { FetchData } from '../../utils/FetchData';
import Auth from '../../auth';
import { useNavigate } from 'react-router-dom';

interface Comment extends CommentProps {
  comments: CommentProps[];
  setComments: any;
}

const Comments = ({ comment, comments, setComments }: Comment) => {
  const [formattedDate, formattedTime] = comment?.formattedDate?.split(' ');
  const [formattedUpdatedDate, formattedUpdatedTime] =
    comment?.formattedUpdatedDate?.split(' ');
  const navigation = useNavigate();
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

  const handleEditComment = async () => {
    navigation(`/blog-details/${comment.postId}?edit=${comment.id}`);
  };

  return (
    <>
      <div
        key={comment.id}
        className='w-full max-w-3xl bg-white shadow-lg rounded-lg overflow-hidden'
      >
        <div className='relative flex p-6 bg-slate-300'>
          <p className='text-base'>
            - <span className='font-bold mr-1'>{comment.user}</span>
          </p>
          <p className='text-base'>{formattedDate}</p>
          {formattedTime !== formattedUpdatedTime ? (
            <p className='text-base ml-2'>
              <span className='font-bold mr-1'>Edited</span>
            </p>
          ) : null}
          {isAdmin || userId === comment?.userId ? (
            <div className='absolute top-0 right-0 p-3'>
              <i
                onClick={handleEditComment}
                className='transition ease-in-out duration-300 hover:bg-slate-100 rounded-lg p-3 text-blue-500 hover:text-blue-600 cursor-pointer text-xl fa-solid fa-pen-to-square'
              ></i>
              <i
                onClick={handleDelete}
                className='transition ease-in-out duration-300 hover:bg-slate-100 rounded-lg p-3 text-red-500 hover:text-red-600 cursor-pointer text-xl fa-solid fa-trash'
              ></i>
            </div>
          ) : null}
        </div>
        <div className='px-6 py-4 '>
          <p>{comment.text}</p>
        </div>
      </div>
    </>
  );
};

export default Comments;
