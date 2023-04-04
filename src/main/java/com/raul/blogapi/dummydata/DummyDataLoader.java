package com.raul.blogapi.dummydata;

import com.raul.blogapi.model.*;
import com.raul.blogapi.repository.CommentRepository;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.RoleRepository;
import com.raul.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DummyDataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create roles
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);


        // Create users
        User adminUser = new User("Raul","jimenezraul","jimenezraul1981@gmail.com", passwordEncoder().encode("password"), LocalDate.of(1990, 1, 1));

        adminUser.setIsEmailVerified(true);
        adminUser.setUpdatedAt();
        adminUser.setCreatedAt();
        User user1 = userRepository.save(adminUser);

        user1.getRoles().add(new Role(adminRole));
        user1.getRoles().add(new Role(userRole));
        userRepository.save(user1);

        User regularUser = new User("Jose","jose","jose@example.com", passwordEncoder().encode("password"), LocalDate.of(1990, 1, 1));
        regularUser.setIsEmailVerified(true);
        regularUser.setUpdatedAt();
        regularUser.setCreatedAt();
        User user2 = userRepository.save(regularUser);

        user2.getRoles().add(new Role(userRole));
        userRepository.save(user2);

        // Create posts
        Post post1 = new Post("Getting Started with React", "<p class=\"font-bold\">React</p> is a popular front-end <span class=\"italic\">JavaScript</span> library for building user interfaces. If you're new to <span class=\"font-bold\">React</span>, don't worry! There are plenty of resources out there to help you get started. Check out the official <a href=\"https://reactjs.org/docs/getting-started.html\" class=\"text-blue-500 hover:underline\">documentation</a> for a step-by-step guide.", user1);
        post1.setCreatedAt();
        post1.setUpdatedAt();
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(new Tag("#react"));
        tags1.add(new Tag("#javascript"));
        post1.setTags(tags1);
        postRepository.save(post1);

        Post post2 = new Post("Python vs JavaScript: Which is Better for Web Development?", "If you're considering learning a programming language for web development, you might be wondering whether to choose <span class=\"font-bold\">Python</span> or <span class=\"font-bold\">JavaScript</span>. While both languages have their strengths and weaknesses, <span class=\"italic\">JavaScript</span> is generally more popular for front-end development, while <span class=\"italic\">Python</span> is often used for back-end development. Ultimately, the choice depends on your goals and the specific project you're working on.", user2);
        post2.setCreatedAt();
        post2.setUpdatedAt();
        List<Tag> tags2 = new ArrayList<>();
        tags2.add(new Tag("#python"));
        tags2.add(new Tag("#javascript"));
        post2.setTags(tags2);
        postRepository.save(post2);

        Post post3 = new Post("How to Write Clean Code in Java", "Writing <span class=\"font-bold\">Java</span> code that is easy to read and maintain can be a challenge, but it's an important skill to master. Some tips for writing clean code include using meaningful variable names, avoiding overly complex functions, and commenting your code effectively. For more tips, check out the <a href=\"https://www.javacodegeeks.com/2014/03/10-tips-to-write-clean-code-in-java.html\" class=\"text-blue-500 hover:underline\">10 Tips to Write Clean Code in Java</a> article.", user2);
        post3.setCreatedAt();
        post3.setUpdatedAt();
        List<Tag> tags3 = new ArrayList<>();
        tags3.add(new Tag("#java"));
        tags3.add(new Tag("#programming"));
        post3.setTags(tags3);
        postRepository.save(post3);

        Post post4 = new Post("The Pros and Cons of Functional Programming", "<span class=\"font-bold\">Functional programming</span> is a programming paradigm that emphasizes the use of pure functions and avoids changing state and mutable data. While functional programming has some advantages, such as being easier to reason about and test, it also has some drawbacks, such as being more difficult to learn for developers who are used to imperative programming. To learn more about the pros and cons of functional programming, check out the <a href=\"https://www.freecodecamp.org/news/functional-programming-principles-for-javascript-developers-79d33b0a16c2/\" class=\"text-blue-500 hover:underline\">Functional Programming Principles for JavaScript Developers</a> article.", user2);
        post4.setCreatedAt();
        post4.setUpdatedAt();
        List<Tag> tags4 = new ArrayList<>();
        tags4.add(new Tag("#functional"));
        tags4.add(new Tag("#programming"));
        post4.setTags(tags4);
        postRepository.save(post4);

        Post post5 = new Post("How to Use the JavaScript Fetch API", "The <span class=\"font-bold\">Fetch</span> API is a modern alternative to the <span class=\"italic\">XMLHttpRequest</span> API for making HTTP requests. The <span class=\"font-bold\">Fetch</span> API is built into modern browsers, and can be used with <span class=\"italic\">JavaScript</span> or <span class=\"italic\">HTML</span>. To learn more about the <span class=\"font-bold\">Fetch</span> API, check out the <a href=\"https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API\" class=\"text-blue-500 hover:underline\">Fetch API</a> documentation.", user1);
        post5.setCreatedAt();
        post5.setUpdatedAt();
        List<Tag> tags5 = new ArrayList<>();
        tags5.add(new Tag("#fetch"));
        tags5.add(new Tag("#api"));
        post5.setTags(tags5);
        postRepository.save(post5);

        Post post6 = new Post("How to Use Git for Version Control", "If you're working on a software development project, you'll likely need to use a <span class=\"font-bold\">version control</span> system like <span class=\"font-bold\">Git</span> to keep track of changes to your code. Git allows you to collaborate with other developers, track changes to your codebase, and revert to previous versions if needed. To learn more about how to use Git, check out the <a href=\"https://git-scm.com/book/en/v2\" class=\"text-blue-500 hover:underline\">Pro Git</a> book.", user1);
        post6.setCreatedAt();
        post6.setUpdatedAt();
        List<Tag> tags6 = new ArrayList<>();
        tags6.add(new Tag("#git"));
        tags6.add(new Tag("#versioncontrol"));
        post6.setTags(tags6);
        postRepository.save(post6);

        Post post7 = new Post("Top 5 Frontend Frameworks for Web Development", "<p>As a frontend developer, choosing the right framework for your project can make a big difference in terms of development time and overall quality of your website or web application. Here are the top 5 frontend frameworks that you should consider:</p>\n" +
                "\n" +
                "<ol>\n" +
                "  <li>\n" +
                "    <h3>React:</h3>\n" +
                "    <p>Developed and maintained by Facebook, React is a popular choice among developers due to its efficient rendering system and extensive community support. It's great for building complex user interfaces and single-page applications.</p>\n" +
                "  </li>\n" +
                "  \n" +
                "  <li>\n" +
                "    <h3>Angular:</h3>\n" +
                "    <p>Angular is a powerful and opinionated framework that provides a comprehensive solution for building large-scale web applications. It has a steep learning curve but offers excellent performance and scalability.</p>\n" +
                "  </li>\n" +
                "  \n" +
                "  <li>\n" +
                "    <h3>Vue.js:</h3>\n" +
                "    <p>A lightweight and easy-to-learn framework, Vue.js is a great option for building smaller projects or adding interactivity to an existing website. It's known for its simplicity and flexibility.</p>\n" +
                "  </li>\n" +
                "  \n" +
                "  <li>\n" +
                "    <h3>Bootstrap:</h3>\n" +
                "    <p>Bootstrap is a popular CSS framework that provides a wide range of pre-built components and styles. It's great for quickly prototyping a design and creating a responsive layout that works across multiple devices.</p>\n" +
                "  </li>\n" +
                "  \n" +
                "  <li>\n" +
                "    <h3>Tailwind CSS:</h3>\n" +
                "    <p>Tailwind CSS is a utility-first CSS framework that allows you to quickly build custom designs without writing any custom CSS. It provides a wide range of pre-built classes for styling common elements and is highly customizable.</p>\n" +
                "  </li>\n" +
                "</ol>\n" +
                "\n" +
                "<p>In conclusion, each of these frameworks has its own strengths and weaknesses, and the best choice for your project will depend on your specific needs and requirements. Take the time to research each one and choose the one that will help you build the best possible website or web application.</p>\n", user1);
        post7.setCreatedAt();
        post7.setUpdatedAt();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("#react"));
        tags.add(new Tag("#javascript"));
        tags.add(new Tag("#frontend"));
        tags.add(new Tag("#library"));
        tags.add(new Tag("#userinterfaces"));
        post7.setTags(tags);
        postRepository.save(post7);

        // Create comments
        Comment comment1 = new Comment("Comment 1", post1,user2);
        comment1.setCreatedAt();
        comment1.setUpdatedAt();
        commentRepository.save(comment1);

        Comment comment2 = new Comment("Comment 2", post1, user2);
        comment2.setCreatedAt();
        comment2.setUpdatedAt();
        commentRepository.save(comment2);

        Comment comment3 = new Comment("I completely agree with you! As a web developer, I've used both Python and JavaScript for various projects, and while they both have their advantages, I find myself leaning more towards JavaScript for front-end development. Its popularity and extensive community support also make it a great choice for web development beginners. However, Python's simplicity and versatility make it a powerful tool for back-end development. It really just depends on what you're trying to achieve and what your personal preferences are.", post2, user1);
        comment3.setCreatedAt();
        comment3.setUpdatedAt();
        commentRepository.save(comment3);

    }

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ;

}
