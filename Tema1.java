package com.example.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tema1 {

	static List<User> useri= new ArrayList<>();
	static List<Questions> intrebari= new ArrayList<>();
	static List<Quizzes> chestionare= new ArrayList<>();


	public static void main(String[] args)  {
		if( args == null ) {
			System.out.println("Hello world!");
			return;
		}

		String[] args2 = new String[100];
		int len = 1;
		args2[0] = "tema1";
		args2[len++] = args[0];
		for( int i = 1; i < args.length; i++ )
		{
			args2[len++] = args[i].substring(0, args[i].indexOf('\'') - 1);
			args2[len++] = args[i].substring(args[i].indexOf('\'') + 1, args[i].length() - 1);
		}
		for( int i = len; i < 100; i++ )
			args2[i] = "";

		if(args2[1].equals("-create-user"))
		{
			if(!args2[2].equals("-u"))
			{
				System.out.println("{'status':'error','message':'Please provide username'}");
				return;
			}
			for(User u: useri)
			{
				if(u.username.equals(args2[3])) {
					System.out.println("{'status':'error','message':'User already exists'}");
					return;
				}
			}
			if(!args2[4].equals("-p"))
			{
				System.out.println("{'status':'error','message':'Please provide password'}");
				return;
			}
			User user= new User();
			user.username=args2[3];
			user.ID=useri.size()+1;
			user.password=args2[5];
			useri.add(user);
			System.out.println("{'status':'ok','message':'User created successfully'}");
		}
		if(args2[1].equals("-create-question"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{'status':'error','message':'You need to be authenticated'}");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{'status':'error','message':'Login failed'}");
				return;
			}

			exists = false;
			for( Questions intreabre: intrebari )
				if( intreabre.question.equals(args2[7]))
					exists = true;
			if(exists)
			{
				System.out.println("{'status':'error','message':'Question already exists'}");
				return;
			}

			exists = false;
			for( String arg : args2 )
				if( arg.contains("answer") )
					exists = true;
			if(!exists)
			{
				System.out.println("{'status':'error','message':'No answer provided'}");
				return;
			}

			for(String arg: args2)
			{
				if( arg.matches("^-answer-[1-9][0-9]*$") )
				{
					int ID= Integer.parseInt(arg.split("-")[2]);
					boolean ok=false;
					for(String arg1: args2)
					{
						if (arg1.equals("-answer-" + ID + "-is-correct")) {
							ok = true;
							break;
						}
					}
					if(!ok) {
						System.out.println("{ 'status' : 'error', 'message' : 'Answer "+arg.split("-")[2]+" has no answer correct flag'}");
						return;
					}
				}
			}
			for(String arg: args2)
			{
				if( arg.contains("-is-correct") )
				{
					int ID= Integer.parseInt(arg.split("-")[2]);
					boolean ok=false;
					for(String arg1: args2)
					{
						if (arg1.equals("-answer-" + ID)) {
							ok = true;
							break;
						}
					}
					if(!ok) {
						System.out.println("{ 'status' : 'error', 'message' : 'Answer "+arg.split("-")[2]+" has no answer description' }");
						return;
					}
				}
			}

			int nr=0;
			for(String arg: args2)
			{
				if( arg.matches("^-answer-[1-9][0-9]*$") )
					nr++;
			}
			if(nr==1) {
				System.out.println("{'status':'error','message':'Only one answer provided'}");
				return;
			}
			else if(nr>5) {
				System.out.println("{'status':'error','message':'More than 5 answers were submitted'}");
				return;
			}

			if(!args2[6].equals("-text"))
			{
				System.out.println("{'status':'error','message':'No question text provided'}");
				return;
			}
			nr=0;
			if(args2[9].equals("single"))
			{
				for(int i= 0 ; i< args2.length; i++)
					if(args2[i].contains("-is-correct") && args2[i+1].equals("1"))
					{
						nr++;
					}
				if(nr>1) {
					System.out.println("{'status':'error','message':'Single correct answer question has more than one correct answer'}");
					return;
				}
			}
			for(int i= 0 ; i< args2.length-1; i++)
				if( args2[i].matches("^-answer-[1-9][0-9]*$") )
					for(int j=i+2;j<args2.length; j++)
						if( args2[j].matches("^-answer-[1-9][0-9]*$") )
							if(args2[i+1].equals(args2[j+1]))
							{
								System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
								return;
							}


			Questions intrebare= new Questions();
			intrebare.question=args2[7];
			intrebare.isSingle=(args2[9].equals("single"));
			intrebare.raspuns = new ArrayList<>();
			for(int i=0; i< args2.length;i++) {
				if(args2[i].matches("^-answer-[1-9][0-9]*$")) {
					Answer raspuns1 = new Answer();
					raspuns1.valoare = Boolean.parseBoolean(args2[i+3]);
					raspuns1.text = args2[i+1];
					intrebare.raspuns.add(raspuns1);
				}
			}
			intrebari.add(intrebare);
			System.out.println("{ 'status' : 'ok', 'message' : 'Question added successfully'}");
		}
		if(args2[1].equals("-get-question-id-by-text"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Login failed' }");
				return;
			}
			exists = false;
			for( Questions intrebare: intrebari )
				if( intrebare.question.equals(args2[7]))
					exists = true;
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Question does not exist' }");
				return;
			}
			for(Questions intrebare1: intrebari)
			{
				if(intrebare1.question.equals(args2[7]))
				{
					System.out.println("{ 'status' : 'ok' , 'message' :'"+(intrebare1.getID() + 1)+"'}");
					return;
				}
			}
		}
		if(args2[1].equals("-get-all-questions"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Login failed' }");
				return;
			}
			System.out.println("{ 'status' : 'ok' , 'message' : '"+ intrebari.toString()+ "'}");

		}
		if(args2[1].equals("-create-quizz"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Login failed' }");
				return;
			}
			exists = false;
			for( Quizzes chestionar: chestionare)
				if( chestionar.name.equals(args2[7]))
					exists = true;
			if(exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Quizz name already exists' }");
				return;
			}
			for(int i=0;i<args2.length;i++) {
				if(args2[i].contains("question")) {
					boolean ok=false;
					for(Questions intrebare: intrebari) {
						if(intrebare.ID + 1==Integer.parseInt(args2[i+1])) {
							ok=true;
						}
					}
					if(ok==false) {
						System.out.println("{ 'status' : 'error' , 'message' : 'Question ID for question "+args2[i].split("-")[2]+" does not exist' }");
						return;
					}
				}
			}
			int nr=0;
			for(int i=0;i< args2.length;i++)
			{
				if(args2[i].contains("question"))
					nr++;
			}
			if(nr>10)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Quizz has more than 10 questions'}");
				return;
			}
			Quizzes chestionar= new Quizzes();
			for(User user: useri)
			{
				if(user.username.equals(args2[3]))
				{
					chestionar.ID_creator=user.ID;
				}
			}
			chestionar.auCompletat=new ArrayList<>();
			chestionar.name=args2[7];
			chestionar.intrebari = new ArrayList<>();
			for(int i=0;i<args2.length;i++)
			{
				if(args2[i].contains("question"))
				{
					for(Questions intrebare:intrebari)
					{
						if(intrebare.ID + 1 ==Integer.parseInt(args2[i+1]))
						{
							chestionar.intrebari.add(intrebare);
						}
					}
				}
			}
			chestionare.add(chestionar);
			System.out.println("{ 'status' : 'ok', 'message' : 'Quizz added succesfully'}");
		}
		if(args2[1].equals("-get-quizz-by-name"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Login failed' }");
				return;
			}
			exists = false;
			for( Quizzes chestionar: chestionare)
				if( chestionar.name.equals(args2[7]))
					exists = true;
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Quizz does not exist' }");
				return;
			}
			for(Quizzes chestionar: chestionare)
			{
				if(args2[7].equals(chestionar.name))
				{
					System.out.println("{ 'status' : 'ok', 'message' : '"+ (chestionar.getID() + 1) + "'}");
				}
			}
		}
		if(args2[1].equals("-get-all-quizzes"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Login failed' }");
				return;
			}
			List<String> chestionarString= new ArrayList<>();
			for(Quizzes chestionar: chestionare)
			{
				chestionarString.add(chestionar.details(args2[2],useri));
			}
			System.out.println("{ 'status' : 'ok', 'message' :'"+ chestionarString+ "'}");
		}
		if(args2[1].equals("-get-quizz-details-by-id"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
				return;
			}
			for(Quizzes chestionar:chestionare)
			{
				if(chestionar.ID + 1==(Integer.parseInt(args2[7])))
				{
					List<String> detaliiIntrebari= new ArrayList<>();
					for(Questions intrebare:chestionar.intrebari)
					{
						detaliiIntrebari.add(intrebare.details(chestionar.intrebari.indexOf(intrebare)+1));
					}
					System.out.println("{ 'status' : 'ok', 'message' :'"+detaliiIntrebari+"'}");
				}
			}
		}
		if(args2[1].equals("-submit-quizz"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{'status' : 'error' , 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			boolean ok=false;
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Login failed' }");
				return;
			}

			if( !args2[6].equals("-quiz-id") )
			{
				System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
				return;
			}

			for(Quizzes chestionar : chestionare )
			{
				if(chestionar.ID + 1==Integer.parseInt(args2[7]))
				{
					ok=true;
				}
			}
			if(!ok)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
				return;
			}

			for(int i=0; i<args2.length; i++)
			{
				if(args2[i].contains("answer-id"))
				{
					ok=false;
					for(Questions intrebare:intrebari)
					{
						for(Answer rasp: intrebare.raspuns)
						{
							if(rasp.ID + 1==Integer.parseInt(args2[i+1]))
							{
								ok=true;
							}
						}
					}
					if(!ok)
					{
						System.out.println("{ 'status' : 'error', 'message' : 'Answer ID for answer i does not exist'}");
						return;
					}
				}
			}

			for(Quizzes chestionar:chestionare)
			{
				if(chestionar.ID + 1==Integer.parseInt(args2[7]))
				{
					for(User user:useri)
					{
						if(user.username.equals(args2[3]))
						{
							if(chestionar.auCompletat.contains(user.ID))
							{
								System.out.println("{ 'status' : 'error', 'message' : 'You already submitted this quizz'}");
								return;
							}
						}
					}
				}
			}
			for(Quizzes chestionar:chestionare)
			{
				if(chestionar.ID==Integer.parseInt(args2[7]))
				{
					for(User user:useri)
					{
						if(user.username.equals(args2[3]))
						{
							if(chestionar.ID_creator==user.ID)
							{
								System.out.println("{ 'status' : 'error', 'message' : 'You cannot answer your own quizz'}");
								return;
							}
						}
					}
				}
			}
		}
		if(args2[1].equals("-delete-quizz-by-id"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p")) {
				System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri) {
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'Login failed' }");
				return;
			}

			if(!args2[6].equals("-id"))
			{
				System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided' }");
				return;
			}

			exists = false;
			for( Quizzes q : chestionare ) {
				if( q.ID + 1 == Integer.parseInt(args2[7])) {
					exists = true;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found' }");
				return;
			}

			for( Quizzes q : chestionare ) {
				if( q.ID + 1 == Integer.parseInt(args2[7])) {
					chestionare.remove(q);
					break;
				}
			}
			System.out.println("{'status':'ok', 'message':'Quizz deleted successfully'}");
		}
		if(args2[1].equals("-get-my-solutions"))
		{
			if(!args2[2].equals("-u") || !args2[4].equals("-p"))
			{
				System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated' }");
				return;
			}
			boolean exists=false;
			for(User u: useri)
			{
				if (u.username.equals(args2[3]) && u.password.equals(args2[5])) {
					exists = true;
					break;
				}
			}
			if(!exists)
			{
				System.out.println("{ 'status' : 'error' , 'message' : 'Login failed' }");
				return;
			}
		}
		if(args2[1].equals("-cleanup-all"))
		{
			System.out.println("\"status\" : \"ok\", \"message\" : \"Cleanup finished successfully\"}");
			User.ResetID();
			Questions.ResetID();
			Answer.ResetID();
			Quizzes.ResetID();
			useri.clear();
			intrebari.clear();
			chestionare.clear();
		}
	}

}