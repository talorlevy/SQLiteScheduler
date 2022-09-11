package android.talorlevy.tjl_pa_final.UI;

import android.content.Context;
import android.content.Intent;
import android.talorlevy.tjl_pa_final.Entity.Course;
import android.talorlevy.tjl_pa_final.Entity.Term;
import android.talorlevy.tjl_pa_final.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start", current.getStart());
                    intent.putExtra("end", current.getEnd());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructor name", current.getInstructorName());
                    intent.putExtra("instructor phone", current.getInstructorPhone());
                    intent.putExtra("instructor email", current.getInstructorEmail());
                    intent.putExtra("term id", current.getTermId());
                    intent.putExtra("optional notes", current.getOptionalNotes());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
            return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null) {
            Course current = mCourses.get(position);
            int id = current.getId();
            String title = current.getTitle();
            holder.courseItemView.setText(id + " " + title);
        }
        else {
            holder.courseItemView.setText("No courses available");
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null) {
            return mCourses.size();
        }
        else return 0;
    }


}
