pub enum METHOD {
    RIGHT,
    CENTER,
    LEFT,
}

pub fn integrate(
    fun: fn(f64) -> f64,
    left_border: f64,
    right_border: f64,
    step: f64,
    method: METHOD,
) -> Option<f64> {
    let mut integral = 0f64;
    let total_iterations = ((right_border - left_border) / step) as u64 - 1;
    for i in 0..=total_iterations {
        let arg = left_border + step * (i as f64);
        let mut val = get_val(&fun, arg, step, &method);
        if val.is_infinite() {
            println!("Integral can not be calculated. Infinite gap found");
            return None;
        }
        if val.is_nan() {
            let eps = step / 5f64;
            val = (get_val(&fun ,arg - eps, step, &method) + get_val(&fun, arg + eps, step, &method)) / 2f64;
            if !val.is_nan() {
                println!("Gap was filled by half-sum of left and right limits");
            } else {
                let method_str:&str = match method {
                    METHOD::CENTER => "Center",
                    METHOD::LEFT => "Left",
                    METHOD::RIGHT => "Right"
                };
                println!("{} rectangles method: Function is undefined on part of the integration range", method_str);
                return None;
            }
        }
        integral = integral + val * step;
    }
    Some(integral)
}

fn get_val(fun: &fn(f64) -> f64, arg: f64, step: f64, method: &METHOD) -> f64 {
    return match method {
        METHOD::RIGHT => fun(arg + step),
        METHOD::CENTER => fun((2f64 * arg + step)/2f64),
        METHOD::LEFT => fun(arg),
    };
}
