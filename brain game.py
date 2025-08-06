import random
import time
import tkinter as tk
from tkinter import messagebox
from tkinter.ttk import Progressbar, Combobox
import matplotlib.pyplot as plt

def generate_question(level):
    if level == 'Easy':
        op1 = random.randint(1, 10)
        op2 = random.randint(1, 10)
        operand = random.choice(['+', '-'])
    elif level == 'Medium':
        op1 = random.randint(11, 20)
        op2 = random.randint(1, 20)
        operand = random.choice(['+', '-', '*'])
    elif level == 'Hard':
        op1 = random.randint(11, 50)
        op2 = random.randint(1, 20)
        operand = random.choice(['+', '-', '*', '//'])
    
    if operand == '+':
        answer = op1 + op2
    elif operand == '-':
        answer = op1 - op2
    elif operand == '*':
        answer = op1 * op2
    elif operand == '//':
        while op2 == 0:
            op2 = random.randint(1, 11)
        answer = op1 // op2
    
    question = f"{op1} {operand} {op2}"
    return question, answer

class MathQuizApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Math Quiz")
        self.root.config(bg='#e0f7fa')
        self.root.state('zoomed') 

        self.no_of_questions = 0
        self.correct_answers = 0
        self.questions_answered = 0
        self.start_time = 0
        self.current_answer = 0
        self.question_time = 0
        self.level = 'Easy'

        self.create_widgets()

    def create_widgets(self):
        self.intro_label = tk.Label(self.root, text="Welcome to the Math Quiz!", font=("Arial", 24, "bold"), bg='#e0f7fa', fg='#00796b')
        self.intro_label.pack(pady=20)

        self.instruction_label = tk.Label(self.root, text="Enter the number of questions, select difficulty level, and press Start Quiz.", font=("Arial", 18), bg='#e0f7fa', fg='#004d40', wraplength=800)
        self.instruction_label.pack(pady=10)

        self.q_label = tk.Label(self.root, text="How many questions?", font=("Arial", 18), bg='#e0f7fa', fg='#004d40')
        self.q_label.pack(pady=5)

        self.q_entry = tk.Entry(self.root, font=("Arial", 18), bg='#ffffff', fg='#00796b', width=10)
        self.q_entry.pack(pady=5)
        self.q_entry.bind("<Return>", self.start_quiz)

        self.level_label = tk.Label(self.root, text="Select difficulty level:", font=("Arial", 18), bg='#e0f7fa', fg='#004d40')
        self.level_label.pack(pady=5)

        self.level_combobox = Combobox(self.root, values=["Easy", "Medium", "Hard"], state="readonly", font=("Arial", 18))
        self.level_combobox.current(0)
        self.level_combobox.pack(pady=5)

        self.start_button = tk.Button(self.root, text="Start Quiz", command=self.start_quiz, font=("Arial", 18, "bold"), bg='#00796b', fg='#ffffff')
        self.start_button.pack(pady=20)

        self.question_label = tk.Label(self.root, text="", font=("Arial", 22), bg='#e0f7fa', fg='#004d40')
        self.answer_entry = tk.Entry(self.root, font=("Arial", 22), bg='#ffffff', fg='#00796b', width=10)
        self.submit_button = tk.Button(self.root, text="Submit Answer", command=self.check_answer, font=("Arial", 18, "bold"), bg='#00796b', fg='#ffffff')

        self.timer_label = tk.Label(self.root, text="", font=("Arial", 18), bg='#e0f7fa', fg='#d32f2f')
        self.progress = Progressbar(self.root, orient=tk.HORIZONTAL, length=400, mode='determinate')

        self.reset_button = tk.Button(self.root, text="Reset Quiz", command=self.reset_quiz, font=("Arial", 18, "bold"), bg='#ff8f00', fg='#ffffff')

        self.answer_entry.bind("<Return>", self.check_answer)

    def start_quiz(self, event=None):
        try:
            self.no_of_questions = int(self.q_entry.get())
        except ValueError:
            messagebox.showerror("Invalid Input", "Please enter a valid number.")
            return

        self.level = self.level_combobox.get()

        self.intro_label.pack_forget()
        self.instruction_label.pack_forget()
        self.q_label.pack_forget()
        self.q_entry.pack_forget()
        self.level_label.pack_forget()
        self.level_combobox.pack_forget()
        self.start_button.pack_forget()

        self.questions_answered = 0
        self.correct_answers = 0
        self.start_time = time.time()
        self.update_progress()

        self.next_question()

    def next_question(self):
        if self.questions_answered < self.no_of_questions:
            question, self.current_answer = generate_question(self.level)
            self.question_label.config(text=question)
            self.answer_entry.delete(0, tk.END)

            self.question_label.pack(pady=20)
            self.answer_entry.pack(pady=10)
            self.submit_button.pack(pady=20)
            self.timer_label.pack(pady=10)
            self.progress.pack(pady=20)

            self.question_time = time.time()
        else:
            self.end_quiz()

    def check_answer(self, event=None):
        try:
            user_answer = int(self.answer_entry.get())
        except ValueError:
            messagebox.showerror("Invalid Input", "Please enter a valid number.")
            return

        if user_answer == self.current_answer:
            self.correct_answers += 1
            self.feedback("Correct!", "#388e3c")
        else:
            self.feedback(f"Incorrect! The correct answer is {self.current_answer}", "#d32f2f")

        self.questions_answered += 1
        self.update_progress()
        self.next_question()

    def feedback(self, message, color):
        feedback_label = tk.Label(self.root, text=message, font=("Arial", 18, "bold"), bg='#e0f7fa', fg=color)
        feedback_label.pack(pady=10)
        self.root.after(1000, feedback_label.destroy)

    def update_progress(self):
        self.progress['value'] = (self.questions_answered / self.no_of_questions) * 100

    def end_quiz(self):
        end_time = time.time()
        time_taken = end_time - self.start_time

        self.question_label.pack_forget()
        self.answer_entry.pack_forget()
        self.submit_button.pack_forget()
        self.timer_label.pack_forget()
        self.progress.pack_forget()

        result_message = (f"You got {self.correct_answers} out of {self.no_of_questions} questions correct.\n"
                          f"You took {time_taken:.2f} seconds.")
        messagebox.showinfo("Quiz Finished", result_message)

        self.plot_results()  
        self.reset_button.pack(pady=20)

    def plot_results(self):
        correct_answers = self.correct_answers
        incorrect_answers = self.no_of_questions - self.correct_answers
        labels = ['Correct', 'Incorrect']
        sizes = [correct_answers, incorrect_answers]
        colors = ['#4CAF50', '#FF5252']

        plt.figure(figsize=(6, 6))
        plt.pie(sizes, labels=labels, autopct='%1.1f%%', colors=colors, startangle=90)
        plt.title('Quiz Result')
        plt.axis('equal')
        plt.show()

        comment = ""
        if correct_answers / self.no_of_questions >= 0.7:
            comment = "Great job! You did really well!"
        elif correct_answers / self.no_of_questions >= 0.5:
            comment = "Good effort! You're improving!"
        else:
            comment = "Keep practicing! You'll get better with time."

        messagebox.showinfo("Result Comment", comment)

    def reset_quiz(self):
        self.intro_label.pack(pady=20)
        self.instruction_label.pack(pady=10)
        self.q_label.pack(pady=5)
        self.q_entry.delete(0, tk.END)
        self.q_entry.pack(pady=5)
        self.level_label.pack(pady=5)
        self.level_combobox.current(0)
        self.level_combobox.pack(pady=5)
        self.start_button.pack(pady=20)

root = tk.Tk()
app = MathQuizApp(root)
root.mainloop()
