use std::thread;

pub mod integrator;
use integrator::METHOD;
pub mod my_io;

fn main() {
    let mut funcs: Vec<(fn(f64) -> f64, &str)> = Vec::new();
    funcs.push((|x| x * x + 5f64, "y = x^2 + 5"));
    funcs.push((|x| -x * x + 9f64, "y = -x^2 + 9"));
    funcs.push((|_x| 5f64, "y = 5"));
    funcs.push((|x| x + 7f64, "y = x + 7"));
    funcs.push((|x| -0.5 * x + 6f64, "y = -0.5x + 6"));
    funcs.push((|x: f64| -> f64 { x.sqrt() }, "y = sqrt(x)"));
    funcs.push((|x| {if x < 0f64 {2f64} else if x > 0f64 {-2f64} else {return f64::NAN}}, "y = 2, x < 0; y = -2, x > 0"));

    println!("Select one of these functions: ");
    my_io::print_funcs(&funcs, 1);
    
    let selected_func_index = my_io::get_index(Some(funcs.len())) - 1;
    let selected_func = funcs[selected_func_index];
    println!("Enter integration borders");
    println!("From: ");
    let a = my_io::get_double();
    println!("To: ");
    let b = my_io::get_double();
    println!("Enter step:");
    let step = my_io::get_double();
    println!("Performing integration by rectangular methods");
    let right_rects_thread = thread::spawn(move || {
        let i: Option<f64> = integrator::integrate(selected_func.0, a, b, step, METHOD::RIGHT);
        i
    });
    let center_rects_thread = thread::spawn(move || {
        let i = integrator::integrate(selected_func.0, a, b, step, METHOD::CENTER);
        i
    });
    let left_rects_thread = thread::spawn(move || {
        let i = integrator::integrate(selected_func.0, a, b, step, METHOD::LEFT);
        i
    });
    let right_rects: Option<f64> = right_rects_thread.join().unwrap();
    if right_rects == None {
        center_rects_thread.join();
        left_rects_thread.join();
        println!("unable to calculate integral");
        return;
    }
    let left_rects: Option<f64> = left_rects_thread.join().unwrap();
    let center_rects = center_rects_thread.join().unwrap();
    println!("Integral by right rectangles: {}", right_rects.unwrap());
    println!("Integral by center rectangles: {}", center_rects.unwrap());
    println!("Integral by left rectangles: {}", left_rects.unwrap());
}
