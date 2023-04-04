import { useState, useEffect } from 'react';
import { useAppDispatch } from '../../app/hooks';
import { setAlert } from '../../app/features/alertSlice';
import { FetchData } from '../../utils/FetchData';
import { useLocation, useNavigate } from 'react-router-dom';

const CommentForm = ({ postId, comments, setComments }: any) => {
  const navigate = useNavigate();
  const location = useLocation();
  const editId = new URLSearchParams(location.search).get('edit');
  const [comment, setComment] = useState('');
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (editId) {
      const comment = comments.find(
        (comment: any) => comment.id === Number(editId)
      );
      if (comment) {
        setComment(comment.text);
      }
    }
  }, [editId, comments]);

  const handleCommentChange = (event: any) => {
    setComment(event.target.value);
  };

  const handleCommentSubmit = async (event: any) => {
    event.preventDefault();
    // check if comment is empty and if is less than 10 characters
    if (comment.length < 5 || comment === '') {
      if (comment === '') {
        dispatch(
          setAlert({
            message: 'Comment cannot be empty',
            type: 'WARNING',
            show: true,
          })
        );
        return;
      }
      dispatch(
        setAlert({
          message: 'Comment must be at least 5 characters long',
          type: 'WARNING',
          show: true,
        })
      );

      return;
    }

    try {
      if (editId) {
        const editComment = comments.find(
          (comment: any) => comment.id === Number(editId)
        );

        const res = await FetchData(`/api/v1/comments/${editId}`, 'PUT', {
          text: comment,
          postId: postId,
          userId: editComment.userId,
        });

        const newComments = comments.map((comment: any) =>
          comment.id === Number(editId)
            ? {
                ...editComment,
                text: res.text,
                formattedUpdatedDate: res.formattedUpdatedDate,
              }
            : comment
        );
        setComments(newComments);
        setComment('');
        navigate(`/blog-details/${postId}`);
        return;
      }

      const res = await FetchData(`/api/v1/comments`, 'POST', {
        text: comment,
        postId: postId,
      });

      if (!res.ok) {
        throw new Error('Something went wrong');
      }

      const data = await res.json();

      setComments([data, ...comments]);
    } catch (error) {
      console.log(error);
    }

    setComment('');
  };

  const handleCancel = () => {
    setComment('');
    navigate(`/blog-details/${postId}`);
  };
  return (
    <div className='bg-slate-100  p-2 border border-gray-300 rounded w-full max-w-3xl shadow'>
      <label className='block font-semibold mb-2' htmlFor='comment'>
        New comment
      </label>
      <textarea
        className='w-full p-2 border border-gray-300 rounded shadow'
        name='comment'
        id='comment'
        rows={3}
        value={comment}
        onChange={handleCommentChange}
      ></textarea>
      <div className='flex justify-end space-x-3'>
        {editId && (
          <button
            className='transition duration-300 px-3 py-2 mt-4 bg-red-400 text-white rounded shadow-md hover:bg-red-600 focus:outline-none'
            onClick={handleCancel}
          >
            Cancel
          </button>
        )}
        <button
          className='transition duration-300 px-3 py-2 mt-4 bg-blue-400 text-white rounded shadow-md hover:bg-blue-600 focus:outline-none'
          aria-label='Add comment'
          onClick={handleCommentSubmit}
        >
          {editId ? 'Update comment' : 'Add comment'}
        </button>
      </div>
    </div>
  );
};

export default CommentForm;
