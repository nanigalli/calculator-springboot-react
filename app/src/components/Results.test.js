import { render, screen } from '@testing-library/react';
import Results from './Results';

const expected1Result = [
    {
        operator: "add",
        leftNumber: "1",
        rightNumber: "2",
        result: "3"
    }
]

test('render 1 result', () => {
    render(<Results oldResults={expected1Result} />);
    const linkElement = screen.getByText('1 + 2 = 3');
    expect(linkElement).toBeVisible;
});

const expectedManyResult = [
    {
        operator: "add",
        leftNumber: "1",
        rightNumber: "2",
        result: "3"
    },
    {
        operator: "divide",
        leftNumber: "6",
        rightNumber: "3",
        result: "2"
    }
]

test('render many results', () => {
    render(<Results oldResults={expectedManyResult} />);
    const linkElement1 = screen.getByText('1 + 2 = 3');
    expect(linkElement1).toBeVisible;
    const linkElement2 = screen.getByText('6 / 3 = 2');
    expect(linkElement2).toBeVisible;
});